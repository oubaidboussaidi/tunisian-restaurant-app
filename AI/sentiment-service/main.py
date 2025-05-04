from fastapi import FastAPI
from pydantic import BaseModel
from transformers import pipeline

app = FastAPI()

# Use a sentiment model that includes "neutral"
sentiment_analyzer = pipeline("sentiment-analysis", model="cardiffnlp/twitter-roberta-base-sentiment")

class Review(BaseModel):
    text: str

@app.post("/analyze")
async def analyze_sentiment(review: Review):
    result = sentiment_analyzer(review.text)
    
    # Map Hugging Face labels to expected output format
    label_map = {
        "LABEL_0": "NEGATIVE",
        "LABEL_1": "NEUTRAL",
        "LABEL_2": "POSITIVE"
    }

    sentiment_label = label_map[result[0]["label"]]
    score = round(result[0]["score"], 3)  # Keep 3 decimal places like your original logic
    
    return {
        "sentiment": sentiment_label,
        "score": score
    }

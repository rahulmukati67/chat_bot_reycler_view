package com.example.chat_bot_recyclerview.model

data class ChatbotData(
    val answer: Message,
    val question: String
)


/*
[
  {
    "type": "wrapup",
    "question": "How I always wake up tired. What should I do?",
    "response": {
      "message": {
        "text": [
          "For improving your sleep and addressing your tiredness, you may consider the following:\n\n- Establish a consistent sleep schedule by going to bed and waking up at the same time each day.\n- Create a peaceful sleep environment; keep the room dark, cool, and quiet to facilitate better rest.\n- Limit screen time before bed to reduce blue light exposure and engage in calming activities like reading or meditation.\n\nFor a holistic view of your sleep health and to identify risk factors, consider taking our [sleep assessment](https://www.resmed.com.au/online-sleep-assessment)."
        ],
        "underLineText": ["holistic view", "addressing your tiredness"],
        "related_questions": []
      }
    }
  },
  {
    "type": "wrapup",
    "question": "What should I do if my CPAP isn't working?",
    "response": {
      "message": {
        "text": [
          "Dryness may arise from various factors such as air leaks, humidity levels, or other health conditions; it may be helpful to consult your healthcare provider for recommendations.\n- Consider adjusting the humidity levels on your CPAP device or ensuring that your mask and tubing are secure and free of any leaks.\n- If issues persist, reaching out to your healthcare provider or equipment provider may provide further insights into managing your CPAP therapy effectively."
        ],
        "underLineText": ["Limit"],
        "related_questions": [
          {
            "type": "related-question-text",
            "data": "How that my CPAP machine causes dry out at night?",
            "text": "Why do I find that my CPAP machine causes my mouth to dry out at night?"
          },
          {
            "type": "related-question-text",
            "data": "What adjustments can I make to my CPAP settings to reduce nasal congestion?",
            "text": "What adjustments can I make to my CPAP settings to reduce nasal congestion?"
          }
        ]
      }
    }
  },
  {
    "type": "wrapup",
    "question": "What do I do when I can't register my device with myAir?",
    "response": {
      "message": {
        "text": [
          "You might encounter issues registering if myAir services are temporarily unavailable. \n- It may be helpful to wait a few hours and then attempt to log in again.\n\nFor further assistance with myAir, please contact our [support team](mailto:myairsupportanz@resmed.com.au)."
        ],
        "underLineText": ["temporarily unavailable"],
        "related_questions": []
      }
    }
  },
  {
    "type": "wrapup",
    "question": "I'd like to submit a complaint.",
    "response": {
      "message": {
        "text": [
          "For concerns that surpass my digital capabilities, our human team is here to assist. [Email](mailto:service@resmed.com.au) with your product details, name, location, and a comprehensive complaint description."
        ],
        "underLineText": [],
        "related_questions": [
          {
            "type": "related-question-text",
            "data": "How can I use the correct CPAP mask for my needs to minimize discomfort?",
            "text": "How can I ensure that I am using the correct CPAP mask for my needs to minimize discomfort?"
          }
        ]
      }
    }
  },
  {
    "type": "wrapup",
    "question": "How that my CPAP machine causes dry out at night?",
    "response": {
      "message": {
        "text": [
          "Your CPAP machine may cause dryness due to insufficient humidity levels. Consider adjusting the humidifier settings or using a heated tube.\n\nIf the issue persists, consult your healthcare provider."
        ],
        "underLineText": ["humidity levels", "consult your healthcare provider"],
        "related_questions": []
      }
    }
  },
  {
    "type": "wrapup",
    "question": "How can I use the correct CPAP mask for my needs to minimize discomfort?",
    "response": {
      "message": {
        "text": [
          "For improving your sleep and addressing your tiredness, you may consider the following:\n\n- Establish a consistent sleep schedule by going to bed and waking up at the same time each day.\n\n- Create a peaceful sleep environment; keep the room dark, cool, and quiet to facilitate better rest.\n\n- Limit screen time before bed to reduce blue light exposure and engage in calming activities like reading or meditation."
        ],
        "underLineText": ["sleep schedule", "blue light exposure"],
        "related_questions": []
      }
    }
  },
  {
    "type": "wrapup",
    "question": "Unrelated question",
    "response": {
      "message": {
        "text": [
          "My virtual hands are tied on this one, I'm afraid. For a more human touch, our [Contact Us form](https://www.resmed.com.au/support-service?utm_source=VA&utm_medium=referral) will connect you with the assistance you require."
        ],
        "underLineText": ["support service", "human touch"],
        "related_questions": [
          {
            "type": "related-question-text",
            "data": "What can I ask you?",
            "text": "Why do I find that my CPAP machine causes my mouth to dry out at night?"
          },
          {
            "type": "related-question-text",
            "data": "Tell me about ResMed's products",
            "text": "What adjustments can I make to my CPAP settings to reduce nasal congestion?"
          },
          {
            "type": "related-question-text",
            "data": "Tell me about ResMed's products",
            "text": "How can you help me?"
          }
        ]
      }
    }
  }
]
 */
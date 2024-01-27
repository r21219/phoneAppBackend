What the app has to have
* one window for chatting

For problem solving
* WireFrames
* Description of the problem (user stories, scenarios, diagrams)
* Design flaws
* Selection of technology
* Architecture design
* Proof of Concept
* Add functionality during development
* Presentation

```mermaid
sequenceDiagram
    participant FE
    participant BE
    FE->>BE: [POST] Registers user to the app
    FE->>BE: [POST] Login to the app
    FE->>BE: [POST] Create group/chat
    BE->>FE: Sends confirmation
    FE->>BE: [GET] Get all groups/chats for logged in user
    FE->>BE: [POST] Posts a message
    BE->>FE: [WS] Sends RoutingKey and TopicName
    FE->>BE: [GET] Get conversation by RoutingKey and TopicName
```
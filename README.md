# nostr-java

This is a project to build a decentralized social network aggregator. This project to build a nostr client, nostr server and event aggregator to subscribe to different nostr relay.

This project comes in different phases, please refer to each phase object to check the deliverable. 


Project detail: [DISTRISE](https://achq.notion.site/Distributed-Systems-Project-Briefing-00eaa7a219954bb1a346d73bf09164f2)

# Phase 1

### Task
build a basic nostr client that can connect to a nostr relay to send a simple message.

### Deliverable
- after spin up application need to call `http://localhost:3000/nostr/connect` to subscribe to relay
- after subscribe to relay, need to call `http://localhost:3000/nostr/hello` to send message to relay
- check on https://relay.nekolicio.us/, to make sure message been sent

![img.png](img.png)

### Questions
- What are some of the challenges you faced while working on Phase 1?

1. I was struggling to write it in Java, I have tried two days to understand how to set up websocket connection in Java but everytime when I send message it terminate the session.
2. In the end, I have decide to submit the code in Javascript as it have a well-developed library in npm
3. As I am new to websocket connection. I am quite lots in the beginning but in the end I have summerize the step I need to do. create websocket connection > serialized event > send event
4. In Javascript I dont need to write up the serialization detail but but I was looking that into Java I learnt a lot on SHA256 hashing.

- What kind of failures do you expect to a project such as DISTRISE to encounter?

1. How would the event aggregator behave when there are massive message coming in and how that pass message to User's own relay.
2. Single failure point on Event aggregator. If it goes down, then means all of the even will get lost?
3. If any of the relay stop updating, shall we inform user?
4. if user relay goes down, means we dont end the connection safely, how will nostr server behave?


# Phase 2

### Task
build nostr relay server for DISTRISE. Relays must accept events from clients and provide ways for people to filter events through subscriptions.

### Deliverable
- I have created a nostr relay endpoint on `ws://localhost:8081/myHandler`
- spin up application and bring up the UI on `http://localhost:3000/index.html`. This page is showing all of the event sent to /myHandler
- In NostrController file, cahange the connection destination to `ws://localhost:8081/myHandler` and run the application
- after spin up application need to call `http://localhost:3000/nostr/connect` to subscribe to relay
- after subscribe to relay, need to call `http://localhost:3000/nostr/hello` to send message to relay
- check on http://localhost:3000/index.html`, to make sure message been sent


### Questions
- Why did you choose this database?

I choose MySQL because this project is quite small we might distribute it but it wont we a project with big scale. As for now, I choose to use something I am familiar with and easy to configure. If this project get to the stage which will be distributed in a large scale. will consider some other database like CockroachDB or Cassandra.

- If the number of events to be stored will be huge, what would you do to scale the database?

I will use Multi-Primary strategy which been natively supported on MySQL. As its a social media platform, I will assume that the number of write event will be huge. I need to make sure every node can accept write.

# Phase 3

### Task
build a Event Aggregator and fetch events from a sinfle relat and store them in your database of choice

### Deliverable
- After spin up application, call `http://localhost:3000/nostr/agg/connect` to allow event aggregator to connect to relay: wss://relay.nekolicio.us
- After connect to the relat, call `http://localhost:3000/nostr/agg/subscribe` to subscribe message coming from that relay
- Once received message from relay, it will be stored in MySQL database

![img_1.png](img_1.png)

### Questions
- Why did you choose this database? Is it the same or different database as the one you used in Phase 2? Why is it the same or a different one?

I am still using the database as I havent seen issue by processing all of the data coming from 1 relay.

- If the number of events to be stored will be huge, what would you do to scale the database?

MySQL do provide a Multi-Primary strategy which can be used to scale the database.

# Phase 4

### Task
Make event aggregator to fetch events from multiple relays,  push them into a Queue of your choice, fetch these events from the Queue system and store them in the database you have chosen in Phase 3.

### Deliverable
- I choose Kafka to be my queue system. I have created a topic called `nostr-topic`
- To make sure this works, need to have a Kafka server running on local machine (will try to run it on docker later)
- I have created a KafkaProducer to send message to Kafka topic
- I have created a KafkaConsumer to consume message from Kafka topic and store it in MySQL database

[need to add a screenshot here]

### Questions
- Why did you choose this solution?

I would like to try out Kafka as it been heavly used in my work. I would like to try out this new technology.

- If the number of events to be stored will be huge, what would you do to scale your chosen solution?

I would create partition on the topic and have more consumer to consume the message from the topic. As we dont need to make sure message been saved by time, I think at this point it make sense to start to put message into different partition. When querying data, will just grab data based on the createdAt timestamp.

![img_2.png](img_2.png)


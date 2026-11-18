Fitness Tracker — Backend
Backend for a fitness tracking app: log strength, cardio, and stretch workouts, plan your week, and track progress. Built with Java 17 and Spring Boot 3.2.1. Pairs with the React/TypeScript frontend in fitness-tracker-fe.
What it does
Auth — sign up / sign in with JWT access tokens plus refresh-token rotation (cookies), role-based access via Spring Security.
Workouts — log today's workout with strength, cardio, and stretch exercises, each with sets (reps/weight/duration). Exercises are modeled with a shared base and per-type details, converted to DTOs at the controller layer.
Weekly plans — build a week plan out of day plans and planned workouts.
Exercise catalog — a seeded library of exercise details the frontend draws from.
Social features — the app includes a social chat feature; this backend serves the workout and account data behind it.
Stack
Java 17, Spring Boot 3.2.1
Spring Data JPA / Hibernate, Spring Security, JWT (jjwt 0.11.5)
H2 in-memory database by default (SQL Server JDBC driver also on the classpath)
Maven wrapper included — no local Maven install needed
How to run
./mvnw spring-boot:run
Requires Java 17. The app starts on the default Spring Boot port (8080) with an H2 in-memory database, so it runs with no external setup. The H2 console is enabled at /h2-console (JDBC URL jdbc:h2:mem:fitnessdb, user sa).
Project layout
web/ — REST controllers (auth, workouts, weekly plans, exercise details, admin)
service/ — workout, weekly-plan, JWT, and refresh-token logic
domain/ — JPA entities (User, Exercise, TodaysWorkout, WeeklyPlan, …)
dao/ — request/response and workout DTOs
repository/ — Spring Data repositories
security/ — JWT filter and security configuration
AI tools
No AI coding tools were used on this codebase — it was written by hand. (Correct this line if that's wrong.)
Status
Self-directed side project, built Dec 2023 – Jul 2025. It works end to end against the frontend; the H2 setup is for local development, not production.

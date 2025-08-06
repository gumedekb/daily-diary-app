# daily-diary-app
This app lets users securely register, log in, and manage their personal diary entries. It consists of a React frontend and a Java Spring Boot backend, communicating through a REST API protected with JWT authentication.



### How It Works 🛠️

#### 1. 🧠 Backend (Spring Boot)

* **🔐 Authentication**
  Users sign up with a username, email, and password. Passwords are hashed before saving. During login, credentials are verified and a JWT token is issued. This token must be sent with every protected request.

* **🪪 JWT Token**
  The backend creates a JWT containing the username as the subject. It’s a stateless way to identify the user — no sessions needed. The token is validated on each request to ensure security.

* **📓 Diary Management**
  Authenticated users can **create**, **read**, **update**, and **delete** their personal diary entries. Ownership is enforced — only the user who created the entry can modify or delete it.

* **🛡️ Security**
  Powered by Spring Security. It handles:

  * Password encoding (`BCrypt`)
  * JWT parsing and validation
  * Request filtering using custom authentication filters
  * Role-based access control (if needed in future)

---

#### 2. 💻 Frontend (React)

* **📦 Token Handling & API Requests**
  The React app stores the JWT token in `localStorage` and automatically includes it in all Axios requests. That way, the backend knows who’s making the call.

* **🧭 User Flow**
  Users sign up → log in → get redirected to their diary dashboard. From there, they can add, edit, or delete entries with ease. All interactions are synced with the backend API.

* **⚛️ React State**
  Components track:

  * Login status
  * Current user info
  * Diary entries

  So the UI updates instantly as the user interacts with their data.


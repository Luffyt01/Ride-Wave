# 🚖 RideWave - Ride Booking Backend  

## 📌 Overview  

**RideWave** is a ride-booking application backend that allows users to book rides, onboard drivers, and manage roles such as **Admin, Rider, and Driver**. It provides secure authentication, ride management, and role-based access control.  

---

## ✨ Features  

- ✅ **User Authentication** (Signup, Login, Token Refresh) using JWT  
- ✅ **Role-Based Access Control** (Admin, Driver, Rider)  
- ✅ **Driver Management** (Onboarding, Profile, Ride Handling)  
- ✅ **Ride Management** (Request, Accept, Start, End, Cancel)  
- ✅ **Rating System** (Riders can rate Drivers and vice versa)  
- ✅ **Pagination Support** for fetching rides  

---

## 🛠 Tech Stack  

- **Backend:** Java, Spring Boot  
- **Database:** PostgreSQL  
- **Security:** JWT Authentication  
- **ORM:** Hibernate, Lombok  
- **API:** RESTful API  

---

## 📌 ER Diagram  

![ER Diagram](path_to_your_er_diagram.png)  

*(Replace `path_to_your_er_diagram.png` with the actual image path in your repo.)*  

---

## 📌 Flow Diagram  

![Flow Diagram](path_to_your_flow_diagram.png)  

*(Replace `path_to_your_flow_diagram.png` with the actual image path in your repo.)*  

---

## 🔥 API Endpoints  

### 🔑 Authentication (`/auth`)  
- **`POST /signup`** → Register a new user  
- **`POST /login`** → Login and receive JWT tokens  
- **`POST /refresh`** → Refresh access token  
- **`POST /onBoardNewDriver/{user_id}`** *(Admin only)* → Onboard a new driver  

### 🚗 Rider (`/rider`)  
- **`POST /requestRide`** → Request a ride  
- **`POST /cancelRide/{rideId}`** → Cancel a ride  
- **`POST /rateDriver`** → Rate a driver  
- **`GET /getMyProfile`** → Get rider profile  
- **`GET /getMyRides`** → View past rides  

### 🚕 Driver (`/drivers`)  
- **`POST /acceptRide/{rideRequestId}`** → Accept a ride request  
- **`POST /startRide/{rideRequestId}`** → Start a ride  
- **`POST /endRide/{rideRequestId}`** → End a ride  
- **`POST /cancelRide/{rideId}`** → Cancel a ride  
- **`POST /rateRider`** → Rate a rider  
- **`GET /getMyProfile`** → Get driver profile  
- **`GET /getMyRides`** → View past rides  

---

## 👨‍💻 Contributing  

1. **Fork** the repository  
2. **Create** a feature branch  
3. **Commit & push** your changes  
4. **Submit** a Pull Request  

---



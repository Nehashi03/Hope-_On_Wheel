# Hope on Wheel – Backend (JWT + Roles)

This is a **complete backend** for the Smart Ambulance Provider app with **JWT authentication** and **role-based authorization**.

## Features
- JWT (`Authorization: Bearer <token>`) with HS512, exp configurable
- Roles: **PATIENT**, **DRIVER**, **ADMIN**
- Entities: Patient, Driver, Booking
- Admin user bootstrapped from properties (defaults for dev)
- Spring Security stateless + method-level authorization (`@PreAuthorize`)
- H2 (default) or MySQL (profile `mysql`, port 3307)

## Run

```bash
mvn spring-boot:run
# or with MySQL
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

### Configure JWT
Edit `src/main/resources/application.properties`:
```
app.jwt.secret=<BASE64_HS_SECRET>
app.jwt.exp-min=240
```
> Generate a new secret: `openssl rand -base64 64`

### Default Admin
On startup, creates an admin using:
```
app.admin.email=admin@how.local
app.admin.password=Admin@123
```
Change these in production.

## API

### Auth
- `POST /api/auth/register/patient`
- `POST /api/auth/register/driver`
- `POST /api/auth/login` → body: `{ email, password, userType: "PATIENT"|"DRIVER"|"ADMIN" }`  
  Response:
  ```json
  { "token":"...", "userType":"DRIVER", "userId":2, "name":"Rahul", "roles":["DRIVER"], "expiresAtEpochSeconds": 1716042015 }
  ```

_Use the JWT in requests:_ `Authorization: Bearer <token>`

### Bookings (JWT required)
- `POST /api/bookings` (PATIENT) create
- `GET  /api/bookings/me` (PATIENT/DRIVER) list own bookings
- `POST /api/bookings/{bookingId}/assign` (DRIVER self‑assign or ADMIN)
- `PATCH /api/bookings/{bookingId}/status` (DRIVER—assigned booking; PATIENT—cancel own; ADMIN—any)
- `POST /api/bookings/{bookingId}/location` (any authenticated) broadcast location to SSE subscribers

### Drivers (JWT required)
- `GET  /api/drivers/available` (any authenticated)
- `PATCH /api/drivers/me/availability?available=true|false` (DRIVER)
- `POST /api/drivers/me/location` (DRIVER)
- `GET  /api/drivers/track/{bookingId}` (SSE stream; any authenticated)

## Security Rules (high-level)
- PATIENT: create bookings, view own, cancel own.
- DRIVER: self-assign booking, update status for assigned bookings, set availability, update location.
- ADMIN: can assign any driver and update any booking status.

---

If you want me to convert this into your existing package names or integrate your earlier partial code, say the word and I’ll generate that diff.

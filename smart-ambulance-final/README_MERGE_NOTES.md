# Smart Ambulance — merged backend + frontend

This repository combines **your backend** (`smart-ambulance-session 2`) with **your friend's frontend** (`hopeonwheel01`) into a single, ready-to-run Spring Boot app.

> ✅ I did **not** change your `pom.xml` or the Java version settings.

## What I merged

- Copied your friend's UI **templates** into:  
  `src/main/resources/templates/`  
  (e.g., `cdashboard.html`, `ddashboard.html`, `booking.html`, `track.html`)

- Copied your friend's **static assets** into:  
  `src/main/resources/static/`  
  (CSS under `static/css/`, JS under `static/js/`)

- **Fixed resource paths** inside the new templates so Spring Boot can serve them:  
  - `style.css` → `/css/style.css`  
  - `css/...` → `/css/...`  
  - `js/...` → `/js/...`  
  - `bookambulance.css/js` → `booking.css/js`  
  - `driverdashboard.css/js` → `/css/ddashboars.css` and `/js/ddashboard.js`

- Added a small controller so login redirects work regardless of role:
  - `/patient/dashboard` → `cdashboard.html`  
  - `/driver/dashboard` → `ddashboard.html`

  See: `src/main/java/com/smartambulance/controller/DashboardController.java`

> Existing routes also continue to work:
> - `/patient` → `cdashboard.html`  
> - `/patient/book` → `booking.html`  
> - `/patient/track` → `track.html`  
> - `/driver` → `ddashboard.html`

## How to run

You need **Java 21+** and **Maven**.

**Option A (Maven wrapper):**
```bash
./mvnw spring-boot:run
# Windows: mvnw.cmd spring-boot:run
```

**Option B (local Maven):**
```bash
mvn spring-boot:run
```

The app starts on **http://localhost:8080**.

### Build a jar
```bash
mvn -DskipTests package
java -jar target/smart-ambulance-1.0.0.jar
```

## Notes / tips

- Static resources are served from `/css/**` and `/js/**`.  
  If you see blocked CSS/JS due to security, ensure your `SecurityConfig`
  permits those paths (e.g., `.requestMatchers("/css/**","/js/**","/images/**","/webjars/**").permitAll()`).

- Default users (if any) are seeded in:
  `src/main/java/com/smartambulance/config/DataInitializer.java`.

- I **did not** touch database settings. Current config uses an H2 file DB:
  `jdbc:h2:file:./data/smartdb`


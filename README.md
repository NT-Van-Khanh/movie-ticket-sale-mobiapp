# Movie Ticket Sale - Mobile App
Movie Ticket Sale - Mobile App is a mobile application developed using Android Java, allowing users to browse movies, book tickets, select seats and snacks, and simulate online payments with a user-friendly and modern interface. 

## Features
### Movie Browsing & Details
- View currently showing and upcoming movies across cinema clusters.
- Search movies by title, description, director, or actors.
- Display full movie information: poster, trailer, genre, duration, age rating, release date, director, cast, synopsis, language, subtitles, and star ratings.
- Display promotional banners and notifications.

### Showtimes
- Allow selection of showtimes in the following order: choose a theater → select date → show list of movies and formats (dubbed, voice-over, subtitled, etc.) → select screening time.
- Alternatively, allow selection starting from movie → choose date → display list of theaters and formats → select screening time.
  
### Ticket and Snack Booking
- Display available ticket types (standard, promotional, student, etc.).
- Show seat map and allow seat selection.
- Show list of available snacks (popcorn, soft drinks, etc.), with quantity selection.
- Display total cost, including tickets and snacks.

###  Payment and Ticket Confirmation
- Display available online payment methods.
- Simulate online payment process.

### User Account Management
- Register, login, recover password, change password, and edit profile information.
- View booking history and QR code for theater check-in.
- Track ticket status: booked, expired.

###  Movie Rating and Reviews
- Allow users to rate and write reviews after watching a movie.
- Show overall community rating for each movie.
 
## Technologies
- **Frontend**: Android (Java)
- **Backend**: [Spring Boot API (GitHub Repo)](https://github.com/daophanquochoai/SellTicket)
- **Database**: MySQL
- **Payment Integration**: Simulated using Stripe SDK
  
## Libraries & Tools
- Retrofit – REST API communication
- Gson & Converter – JSON parsing and serialization
- OkHttp Logging Interceptor – HTTP request/response logging
- Glide – Image loading and caching
- AndroidX Lifecycle & ViewModel – MVVM architecture support
- Flexbox Layout – Responsive UI layouts
- Google Maps SDK – Cinema location selection
- Google Location Services – User location tracking
- Stripe SDK – Payment processing (simulated)

## UI Design:
1. Home screen, Movies screen and Search screen
<img src="https://github.com/user-attachments/assets/2b1393ff-216a-42e2-bd60-55e146434251" width="200">
<img src="https://github.com/user-attachments/assets/8b3e98d8-ce45-4988-b749-6d8bf7e08fc1" width="200">
<img src="https://github.com/user-attachments/assets/abe883ef-63cb-467d-b4ad-819be91b4ce2" width="200">

2. Detail movie screen and movie showtimes by movie
<img src="https://github.com/user-attachments/assets/d8181881-28fe-4b46-91f6-27a4e76a8bb6" width="200">
<img src="https://github.com/user-attachments/assets/de101585-c06a-45fc-b4b0-2a62737c13b7" width="200">

3. Theaters screen and movie showtimes by theater
<img src="https://github.com/user-attachments/assets/7725cc4a-1290-459c-861e-83ba97e013e7" width="200">
<img src="https://github.com/user-attachments/assets/2f92b6f7-3b0d-4f3d-bda1-776c23ff1f88" width="200">

4. Choose ticket, choose seat and choose food screens
<img src="https://github.com/user-attachments/assets/1e19a8f3-c35c-4246-ae24-ef57c73fd60a" width="200">
<img src="https://github.com/user-attachments/assets/8f2cc65b-4943-48c9-9056-3e61853c7774" width="200">
<img src="https://github.com/user-attachments/assets/d7348023-07ea-4250-8fac-9e2986046474" width="200">

5. Confirm order 
<img src="https://github.com/user-attachments/assets/9dfeb525-da8b-47fd-a18b-7eb7fb7d50f6" width="200">
 
7. Auth sreens (login, register, reset password, OTP verification)
<img src="https://github.com/user-attachments/assets/ec92fbba-eff5-4c0a-a298-1b0f9e4c1168" width="200">
<img src="https://github.com/user-attachments/assets/33cd3a87-976c-4605-99ca-c2e8f7bbd538" width="200">
<img src="https://github.com/user-attachments/assets/f1b66bfa-7e0c-4468-b31b-c04e6764078c" width="200">
<img src="https://github.com/user-attachments/assets/9495a77b-ad76-4e20-b710-935bf183e160" width="200">


8. Personal screen and settings screen (personal, change password, change information, view booking history and ticket detail)
<img src="https://github.com/user-attachments/assets/0d7e8278-3efc-4b5e-b47c-e33816afb799" width="200">
<img src="https://github.com/user-attachments/assets/ffefcec2-3b3f-4d8b-85e5-916d39acb7dc" width="200">
<img src="https://github.com/user-attachments/assets/25b63a2a-03e0-4854-95c6-7e6ed99d4d73" width="200">
<img src="https://github.com/user-attachments/assets/bbf7958b-de9c-41e7-b52e-50ab7ec6daea" width="200">
<img src="https://github.com/user-attachments/assets/1ca80717-174c-4244-97ca-e76ea238a3cd" width="200">

## Getting Started
1. Clone the repository
     ```bash
    git clone https://github.com/NT-Van-Khanh/movie-sticket-sale-mobiapp.git
    ```
2. Open the project in Android Studio

3. Sync dependencies using Gradle

4. Run the application on an emulator or a real device:
   ```bash
    ./gradlew assembleDebug  # Build APK
    ./gradlew installDebug    # Install on a connected device
   ```

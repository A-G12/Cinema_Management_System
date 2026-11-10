# Cinema Management System (Gestión de Cartelera)

Final project for the **Data Structures** course, built in **Java** with **Object-Oriented Programming** and a **Swing** graphical interface.

The system manages a network of **50 cinemas** across **5 Mexican states**. Each cinema has its own movie listings, showtimes per screening room, snack bar orders and search history, and the system can find the nearest cinema. These features run on data structures **implemented from scratch**, without the standard library's `ArrayList`, `LinkedList`, `Queue` or `Stack`.

---

## Features

### Administrator
- **Add movies** with title, director, producer, rating, genre and duration. The administrator also sets how many showtimes the movie will have (1 to 10). After saving, the app moves straight to the showtime form, which shows how many showtimes are still left to add.
- **Add showtimes** with room (1 to 10), time (`HH:mm`) and date (`dd/MM/yyyy`). Both fields use input masks and are validated. The system rejects any showtime that **overlaps** another one in the same room on the same date, and it reserves a **30-minute cleaning buffer** after every screening.
- **Remove movies**, together with all their showtimes, after a confirmation prompt.
- **Remove showtimes** by picking one from the movie's list. Every movie must keep at least one showtime.
- **Edit movies**. If the new duration is longer than the old one, the system checks every existing showtime again for overlaps before saving.
- **Look up movies** and see their details and showtimes.
- **View the listings**, sorted by title or by date and time, in ascending or descending order.
- **Serve the snack bar**: view pending orders and dispatch the next one (FIFO).

### Customer
- **Search movies** by title, by rating or by genre.
- **View the listings** with the same sorting options as the administrator.
- **Look up movies**. Each lookup is saved to the search history, and repeating the same movie twice in a row does not create a duplicate entry.
- **Order snack bar combos**: *Familiar* (family), *Dúo* (duo) or *Personal*.
- **View the search history**, most recent first.
- **Find the nearest cinema** in the same state, using shortest paths over a weighted graph.

### General
- **Login screen for administrators**, limited to 3 attempts before going back to cinema selection.
- **Text-file persistence**: each cinema saves and loads its own data automatically.

---

## Data Structures and Algorithms

| Structure / Algorithm | Class | Purpose |
|---|---|---|
| **Singly linked list** | `ListaPeliculas`, `NodoPelicula` | Each cinema's movie listings (insert, delete, traverse) |
| **Linked queue (FIFO)** | `ColaDulceria`, `NodoCola` | Snack bar orders, served in arrival order |
| **Array-based stack (LIFO)** | `PilaHistorial` | History of looked-up movies (capacity 50) |
| **Static array** | `Pelicula.horarios`, `GestionCines.cines` | Showtimes per movie (max 10) and the catalog of 50 cinemas |
| **Adjacency matrix (weighted graph)** | `GestionCines` | Distances in km between the municipalities of each state |
| **Floyd-Warshall** | `GestionCines.cineMasCercano()` | Shortest paths between every pair of cinemas |
| **QuickSort** | `ListaPeliculas`, `CarteleraGeneral` | Sorts movies by title and the listings by title or date |
| **Binary search** | `ListaPeliculas.busquedaBinaria()` | Finds a movie by title (case-insensitive) |
| **Linear search** | `GestionCines.buscarCine()`, `PantallaCliente` | Finds a cinema by state and municipality; filters movies by rating or genre |

### Time Complexity

| Operation | Complexity |
|---|---|
| Add movie (append to list) | O(n) |
| Remove movie by title | O(n) |
| Search movie by title (sort + binary search) | O(n log n) |
| Filter movies by rating or genre | O(n) |
| Enqueue / dequeue combo | O(1) |
| Push / pop history | O(1) |
| Display history (pop all, then push back) | O(k), where *k* is the history size |
| Showtime overlap check | O(n · h), where *n* is the number of movies and *h* the showtimes per movie |
| Sort listings (QuickSort) | O(m log m) on average, where *m* is the total number of showtimes |
| Floyd-Warshall (per state) | O(V³), where V = 10 municipalities |

---

## Object-Oriented Design

```
GestionCines ──< Cine >── ListaPeliculas ──< NodoPelicula ── Pelicula ──< Horario
                  │
                  ├── ColaDulceria ──< NodoCola
                  └── PilaHistorial

CarteleraGeneral  → flattened view (movie + showtime) used to sort the listings
Proyecto          → entry point (main); applies the Nimbus look and feel and opens the UI
```

- **`GestionCines`**: manages the 50 cinemas and the 5 distance matrices, and finds the nearest cinema.
- **`Cine`**: holds a cinema's movies, snack bar queue and history. It also reads and writes the cinema's data file and checks showtimes for overlaps.
- **`Pelicula`**: stores a movie's details and its array of showtimes.
- **`Horario`**: room, time (`HH:mm`) and date (`dd/MM/yyyy`).
- **Nodes** (`NodoPelicula`, `NodoCola`): the building blocks of the linked structures.

---

## User Interface

The UI is written in code with Swing. It uses a dark theme, and each screen has a side menu that switches between panels through a `CardLayout`.

### Navigation Flow

```
PantallaSeleccioncine  (choose state and municipality)
        │
        ├── Administrator ──> PantallaLogin ──(valid credentials)──> PantallaAdmin
        │                         └──(3 failed attempts)──> back to selection
        │
        └── Customer ───────> PantallaCliente
```

Logging out from either view returns the user to cinema selection.

### Screens

| Class | Description |
|---|---|
| `PantallaSeleccioncine` | Start screen. The user picks a state and municipality, then chooses whether to enter as administrator or customer. |
| `PantallaLogin` | Administrator login. After 3 failed attempts, it returns to cinema selection. |
| `PantallaAdmin` | Administrator panel: add, edit and remove movies and showtimes, view the listings and serve the snack bar queue. |
| `PantallaCliente` | Customer panel: search movies, view the listings, order combos, view the history and find the nearest cinema. |
| `Ventanaprincipal` | Shared theme and component factory: colors, fonts, buttons, text fields, combo boxes and card panels. Every screen uses it. |

The `.form` files are IntelliJ GUI Designer descriptors. The actual layouts are built in code.

---

## Catalogs

| Rating | Code |
|---|---|
| AA, A, B, B15, C, D | `1` – `6` |

| Genre | Code |
|---|---|
| Action, Adventure, Science Fiction, Comedy, Drama, Thriller, Suspense, Horror, Romance, Animation | `1` – `10` |

| Snack bar combo | Code |
|---|---|
| Familiar, Dúo, Personal | `1` – `3` |

---

## States and Municipalities

| Key | State | Municipalities |
|---|---|---|
| `J11` | Jalisco | A – J |
| `N11` | Nuevo León | A – J |
| `E11` | Estado de México | A – J |
| `C11` | Chihuahua | A – J |
| `S11` | Sinaloa | A – J |

Each state has a **10 × 10** matrix of distances in kilometers. When two municipalities have no direct connection, the matrix stores `INF = 999999999`. Floyd-Warshall fills those gaps with the shortest route through other municipalities.

---

## Persistence

Each cinema stores its data in a `.txt` file named `<State>_<Municipality>.txt` (for example, `J11_A.txt`). The file is loaded when the cinema is selected. It is rewritten every time a movie is added, edited or removed, or a showtime is added or removed.

File format:

```
PELICULA
Movie title
Director
Producer
<rating code>
<duration in minutes>
<genre code>
HORARIO
<room>
<time HH:mm>
<date dd/MM/yyyy>
HORARIO
...
PELICULA
...
```

---

## Project Structure

```
Gestion_cartelera/
├── docs/
│   ├── Manual tecnico.pdf          # Technical manual
│   └── Manual usuario.pdf          # User manual
├── releases/
│   └── Proyecto-1.0-SNAPSHOT.jar   # Compiled build
├── src/main/java/
│   ├── com/mycompany/proyecto/
│   │   ├── Proyecto.java           # Entry point (main)
│   │   ├── GestionCines.java       # Cinema network, graphs, Floyd-Warshall
│   │   ├── Cine.java               # Per-cinema logic, overlap check, persistence
│   │   ├── Pelicula.java
│   │   ├── Horario.java
│   │   ├── ListaPeliculas.java     # Linked list, QuickSort, binary search
│   │   ├── NodoPelicula.java
│   │   ├── ColaDulceria.java       # Linked queue
│   │   ├── NodoCola.java
│   │   ├── PilaHistorial.java      # Array-based stack
│   │   └── CarteleraGeneral.java   # QuickSort by title or date
│   └── ui/
│       ├── PantallaSeleccioncine.java       # Cinema selection
│       ├── PantallaLogin.java / .form       # Administrator login
│       ├── PantallaAdmin.java / .form       # Administrator panel
│       ├── PantallaCliente.java / .form     # Customer panel
│       └── Ventanaprincipal.java / .form    # Shared theme and components
├── LICENSE
└── pom.xml
```

---

## Requirements

- **JDK 8** or later (the project uses `java.time`).

---

## Academic Context

- **Course:** Data Structures
- **Program:** Information Systems and Technology Engineering
- **University:** UNIVA (Universidad del Valle de Atemajac)
- **Author:** Angel Gaytán

See the [technical manual](docs/Manual%20tecnico.pdf) and the [user manual](docs/Manual%20usuario.pdf) for more details.

## License

This project is distributed under the terms in the [LICENSE](LICENSE) file.

# PokeManager
An Android application to function as a Pokedex using the PokeApi and the paging library to load the data by pages.

## How to use
First you need to clone the project and run it on Android Studio. The first time you open the app it shows some configuration screens that allows you to choose the names to show for each species of Pokemon and the way two handle data.
There are two modes for this:

### Consult when need it and not save the data: 
It only requests the data when it is needed and doesn’t persist it. Including error screens when failing to get the data on the first try and retry option.

https://github.com/RNicolasMarin/PokeManager/assets/69260571/3940f73b-7faa-4fc1-b62e-eafa11567d41


After that it lists the species and when it's getting to the end loads more species.

https://github.com/RNicolasMarin/PokeManager/assets/69260571/2c9be7e3-fcf4-4e4c-9d68-b90c95d68f4c


When it tries to load the next page of species, if it fails then it shows you a button to retry.

https://github.com/RNicolasMarin/PokeManager/assets/69260571/8cdefb13-5ac8-4205-8f3d-834ba7f1b119


It also shows the loading or retry button if you’re scrolling to the top of the list.

https://github.com/RNicolasMarin/PokeManager/assets/69260571/9c1e4bdc-943a-456d-a589-648f43ab3ed8


When tapping on a specie it shows you the retry screen or the screen with its details if it is successful.

https://github.com/RNicolasMarin/PokeManager/assets/69260571/8735bb39-50e9-472b-92a1-198a3d295284


Among the details shows tabs with different information like stats, moves and evolution stages (by clicking on them you can see the details for that species). It has buttons to the previous/next species and if the current one has any alternative forms it shows a button to switch between them.

https://github.com/RNicolasMarin/PokeManager/assets/69260571/bfb2b499-8149-4417-8c9a-3fb968e17839


It displays differently each specific evolution case according to how many members and which one evolves into which.

https://github.com/RNicolasMarin/PokeManager/assets/69260571/e36f480c-29c7-40ee-ad42-811a1a4f1625


https://github.com/RNicolasMarin/PokeManager/assets/69260571/99a83753-47db-45d2-a064-d758b4a1078d



### Download all the data now: 
When the app starts use a service and custom notification to download all the data from the api and persist it with Room to use it later. It works exactly like the previous mode but it takes longer to start because it needs to download all the information then when it will use it, everything loads faster and without failures for connection.

https://github.com/RNicolasMarin/PokeManager/assets/69260571/97a2b1a0-b04b-42f2-bc79-2447bcfc7831


https://github.com/RNicolasMarin/PokeManager/assets/69260571/06cda232-8a34-4697-8984-ef8a313022dd


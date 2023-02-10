# Dukes Excercise
Aplikacja "Dukes Exercise" została napisana przeze mnie całkowicie w Kotlinie. Jest dostępna na [Play Store (link)](https://play.google.com/store/apps/details?id=zahenta.dukesexercise). Jeśli wolisz oglądać niż czytać, to, zamiast czytania tego pliku możesz, obejrzeć [video]() na którym pokazuje jak aplikacja działa, jak można jej użuwać.

### Co robi aplikacja
![screen1](img/screen1.jpg)<br/>
Aplikacja służy do trenowania uników przed uderzeniami. Aplikacja losouje jeden z sześciu uderzeń (lewy/prawy prosty/sierpowy/hak), "mówi" nazwę uniku, a zadaniem użytkownika jest wykonanie odpowiedniego uniku. W polu "interwał" można ustawić czas między kolejnymi uderzeniami. Przykładowo, jeżeli w polu interwał będzie wpisane 2.0 sekundy, to po wylosowaniu pierwszego uderzenia aplikacja zaczeka 2.0 sekundy, zanim wylosuje drugie uderzenie.<br/>

Użytkownik może wybrać, które ciosy będą losowane z większym prawdopodobieństwiem, a które z mniejszym. Robi to ustawiając prawdopodonieństwa względne danych ciosów. Aplikacja, po wsiscnięciu "Start", sumuje prawdopodobieństwa względne wszystkich ciosów. Następnie, w każdym losowaniu prawdopodobieństwo wylosowania danego ciosu jest równe prawdopodobieństwu względnemu tego ciosu podzielonemu przez sumę wszystkich prawdopodobieństw względnych. Przykładowo, po ustawieniu prawdopodobieństw tak jak na powyższym zdjęciu, prawdopodobieństwo wylosowanie każdego uderzenia będzie takie samo, czyli 1/6. Natomiast, po ustawieniu prawdopodobieństw tak jak na poniższym zdjęciu, "lewy prosty" będzie losowany z prawdopodobieństwem 0.75,  "prawy prosty" z prawdopodobieństwem "0.25", a pozostałe ciosy nie będą losowane.<br/>
![screen2](img/screen2.jpg)<br/>

Aplikacja automatycznie zapisuje swój stan ( wartości wszystkich prawdopodobieństw i interwału). Po zamknięciu i otwarciu aplikacji wartości wszystkich prawdopodobieństw i interwału będą dokładnie takie same jak przed zakmnięciem.

package com.metropolitan.cs330pz3599.pitanja

object Deklaracije {

    const val brojIndeksa: String = "3599"
    const val tacni: String = "TacniOdgovori"
    const val ukupno: String = "UkupnoPitanja"

    fun getSvaPitanja(): ArrayList<Pitanje> {


        val listaPitanja = ArrayList<Pitanje>()

        val pitanje1 = Pitanje(
            0,
            "Komponeta koja rešava navedeni problem upravljanja zavisnostima kroz obezbeđivanje formalizovanih mehanizama uklapanja komponenata u funkcionalnu celinu, naziva se:",
            "Abstract Factory",
            "Spring Framework Inversion of Control",
            "Factory",
            "Builder",
            2
        )

        val pitanje2 = Pitanje(
            1,
            "Spring rešava problem komponenata različitih verzija, koje se javljaju kao posledica tranzitivnih zavisnosti, pomoću:",
            "primenom zavisnosti spring-context",
            "primenom zavisnosti spring-framework-tx",
            "primenom zavisnosti spring-framework-bom",
            "primenom zavisnosti spring-context-tx",
            3
        )

        val pitanje3 = Pitanje(
            2,
            "Spring dozvoljava da se obeleži zrno kandidat za automatsko povezivanje obeležavanjem njegovog naziva anotacijom:",
            "@Resource",
            "@Autowired",
            "@Qualifier",
            "@EnableMVC",
            3
        )

        val pitanje4 = Pitanje(
            3,
            "Kako se zove komponenta koja implementira jedan od centralnih JAVA EE dizajn šablona poznatijih pod nazivom front controller?",
            "Kontroler",
            "ViewResolver",
            "Resolver",
            "Dispatcher Servlet",
            4
        )

        val pitanje5 = Pitanje(
            4,
            "Skeniranje komponenata i automatsko konfigurisanje Spring Boot aplikacije omogućeno je primenom:",
            "anotacije @SpringBootApplication",
            "anotacije @ComponentScan",
            "anotacije @Configuration",
            "anotacije @EnableAutoConfiguration",
            1
        )

        listaPitanja.add(pitanje1)
        listaPitanja.add(pitanje2)
        listaPitanja.add(pitanje3)
        listaPitanja.add(pitanje4)
        listaPitanja.add(pitanje5)
        listaPitanja.shuffle()
        return listaPitanja
    }

}
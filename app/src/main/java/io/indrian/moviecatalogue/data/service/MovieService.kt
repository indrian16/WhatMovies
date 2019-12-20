package io.indrian.moviecatalogue.data.service

import io.indrian.moviecatalogue.data.model.Movie
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MovieService {

    private val movieDummy = listOf(
        Movie(
            id = 0,
            title = "Frozen II",
            poster =  "/qdfARIhgpgZOBh3vfNhWS4hmSo3.jpg",
            backdrop = "/xJWPZIYOEFIjZpBL7SVBGnzRYXp.jpg",
            year = "2019",
            overview = "Elsa, Anna, Kristoff and Olaf head far into the forest to learn the truth about an ancient mystery of their kingdom."
        ),
        Movie(
            id = 1,
            title = "Jumanji: The Next Level",
            poster = "/l4iknLOenijaB85Zyb5SxH1gGz8.jpg",
            backdrop = "/zTxHf9iIOCqRbxvl8W5QYKrsMLq.jpg",
            year = "2019",
            overview = "In Jumanji: The Next Level, the gang is back but the game has changed. As they return to rescue one of their own, the players will have to brave parts unknown from arid deserts to snowy mountains, to escape the world's most dangerous game."
        ),
        Movie(
            id = 2,
            title = "The Irishman",
            poster = "/mbm8k3GFhXS0ROd9AD1gqYbIFbM.jpg",
            backdrop = "/ww5aGS5H2tUtckxFFNOJE2790S7.jpg",
            year = "2019",
            overview = "Pennsylvania, 1956. Frank Sheeran, a war veteran of Irish origin who works as a truck driver, accidentally meets mobster Russell Bufalino. Once Frank becomes his trusted man, Bufalino sends him to Chicago with the task of helping Jimmy Hoffa, a powerful union leader related to organized crime, with whom Frank will maintain a close friendship for nearly twenty years."
        ),
        Movie(
            id = 3,
            title = "Rambo: Last Blood",
            poster = "/kTQ3J8oTTKofAVLYnds2cHUz9KO.jpg",
            backdrop = "/spYx9XQFODuqEVoPpvaJI1ksAVt.jpg",
            year = "2019",
            overview = "After fighting his demons for decades, John Rambo now lives in peace on his family ranch in Arizona, but his rest is interrupted when Gabriela, the granddaughter of his housekeeper María, disappears after crossing the border into Mexico to meet her biological father. Rambo, who has become a true father figure for Gabriela over the years, undertakes a desperate and dangerous journey to find her."
        ),
        Movie(
            id = 4,
            title = "Joker",
            poster = "/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
            backdrop = "/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg",
            year = "2019",
            overview = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure."
        ),
        Movie(
            id = 5,
            title = "Ip Man 4: The Finale",
            poster = "/vn94LlNrbUWIZZyAdmvUepFBeaY.jpg",
            backdrop = "/ekP6EVxL81lZ4ivcqPsoZ72rY0h.jpg",
            year = "2019",
            overview = "Ip Man 4 is an upcoming Hong Kong biographical martial arts film directed by Wilson Yip and produced by Raymond Wong. It is the fourth in the Ip Man film series based on the life of the Wing Chun grandmaster of the same name and features Donnie Yen reprising the role. The film began production in April 2018 and ended in July the same year."
        ),
        Movie(
            id = 6,
            title = "Once Upon a Time… in Hollywood",
            poster = "/8j58iEBw9pOXFD2L0nt0ZXeHviB.jpg",
            backdrop = "/aQLygZOIKai6aaiBAeeKpIWO5nc.jpg",
            year = "2019",
            overview = "Los Angeles, 1969. TV star Rick Dalton, a struggling actor specialized in westerns, and stuntman Cliff Booth, his best friend, try to survive to a constantly changing movie industry. Dalton is neighbor of the young and promising actress and model Sharon Tate, who has just married the prestigious Polish director Roman Polanski…"
        ),
        Movie(
            id = 7,
            title = "Terminator: Dark Fate",
            poster = "/vqzNJRH4YyquRiWxCCOH0aXggHI.jpg",
            backdrop = "/riTANvQ8GKmQbgtC1ps3OfkU43A.jpg",
            year = "2019",
            overview = "Decades after Sarah Connor prevented Judgment Day, a lethal new Terminator is sent to eliminate the future leader of the resistance. In a fight to save mankind, battle-hardened Sarah Connor teams up with an unexpected ally and an enhanced super soldier to stop the deadliest Terminator yet."
        ),
        Movie(
            id = 8,
            title = "It Chapter Two",
            poster = "/zfE0R94v1E8cuKAerbskfD3VfUt.jpg",
            backdrop = "/8moTOzunF7p40oR5XhlDvJckOSW.jpg",
            year = "2019",
            overview = "27 years after overcoming the malevolent supernatural entity Pennywise, the former members of the Losers' Club, who have grown up and moved away from Derry, are brought back together by a devastating phone call."
        ),
        Movie(
            id = 9,
            title = "Abominable",
            poster =  "/20djTLqppfBx5WYA67Y300S6aPD.jpg",
            backdrop = "/tLCDKsXo6D84IVFanoElosSEKdp.jpg",
            year = "2019",
            overview = "A group of misfits encounter a young Yeti named Everest, and they set off to reunite the magical creature with his family on the mountain of his namesake."
        )
    )

    fun getMovies(): Observable<List<Movie>> {

        return Observable.just(movieDummy)
            .delay(3, TimeUnit.SECONDS)
    }
}
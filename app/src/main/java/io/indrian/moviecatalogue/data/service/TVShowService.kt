package io.indrian.moviecatalogue.data.service

import io.indrian.moviecatalogue.data.model.TVShow
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class TVShowService {

    private val tvShowDummy = listOf(
        TVShow(
            id = 0,
            name = "The Mandalorian",
            poster =  "/BbNvKCuEF4SRzFXR16aK6ISFtR.jpg",
            backdrop = "/o7qi2v4uWQ8bZ1tW3KI0Ztn2epk.jpg",
            year = "2019",
            overview = "Set after the fall of the Empire and before the emergence of the First Order, we follow the travails of a lone gunfighter in the outer reaches of the galaxy far from the authority of the New Republic."
        ),
        TVShow(
            id = 1,
            name = "Rick and Morty",
            poster = "/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
            backdrop =  "/mzzHr6g1yvZ05Mc7hNj3tUdy2bM.jpg",
            year = "2013",
            overview = "Rick is a mentally-unbalanced but scientifically-gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school."
        ),
        TVShow(
            id = 2,
            name = "His Dark Materials",
            poster = "/9yKCJTOh9m3Lol2RY3kw99QPH6x.jpg",
            backdrop = "/xOjRNnQw5hqR1EULJ2iHkGwJVA4.jpg",
            year = "2019",
            overview = "Lyra is an orphan who lives in a parallel universe in which science, theology and magic are entwined. Lyra's search for a kidnapped friend uncovers a sinister plot involving stolen children, and turns into a quest to understand a mysterious phenomenon called Dust. She is later joined on her journey by Will, a boy who possesses a knife that can cut windows between worlds. As Lyra learns the truth about her parents and her prophesied destiny, the two young people are caught up in a war against celestial powers that ranges across many worlds."
        ),
        TVShow(
            id = 3,
            name = "Arrow",
            poster = "/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
            backdrop = "/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
            year = "2012",
            overview = "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow."
        ),
        TVShow(
            id = 4,
            name = "The Simpsons",
            poster = "/yTZQkSsxUFJZJe67IenRM0AEklc.jpg",
            backdrop = "/f5uNbUC76oowt5mt5J9QlqrIYQ6.jpg",
            year = "1989",
            overview = "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general."
        ),
        TVShow(
            id = 5,
            name = "The Flash",
            poster = "/vn94LlNrbUWIZZyAdmvUepFBeaY.jpg",
            backdrop = "/6ZdQTBy20HzWudZthAV7NkZWfIb.jpg",
            year = "2014",
            overview = "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \\\"meta-human\\\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash."
        ),
        TVShow(
            id = 6,
            name = "Vikings",
            poster = "/ff1zhqvwfS5HvRNcA5UFrH0PA2q.jpg",
            backdrop = "/aq2yEMgRQBPfRkrO0Repo2qhUAT.jpg",
            year = "2013",
            overview = "The adventures of Ragnar Lothbrok, the greatest hero of his age. The series tells the sagas of Ragnar's band of Viking brothers and his family, as he rises to become King of the Viking tribes. As well as being a fearless warrior, Ragnar embodies the Norse traditions of devotion to the gods. Legend has it that he was a direct descendant of Odin, the god of war and warriors."
        ),
        TVShow(
            id = 7,
            name = "Supernatural",
            poster = "/KoYWXbnYuS3b0GyQPkbuexlVK9.jpg",
            backdrop = "/o9OKe3M06QMLOzTl3l6GStYtnE9.jpg",
            year = "2005",
            overview = "When they were boys, Sam and Dean Winchester lost their mother to a mysterious and demonic supernatural force. Subsequently, their father raised them to be soldiers. He taught them about the paranormal evil that lives in the dark corners and on the back roads of America ... and he taught them how to kill it. Now, the Winchester brothers crisscross the country in their '67 Chevy Impala, battling every kind of supernatural threat they encounter along the way. "
        ),
        TVShow(
            id = 8,
            name = "4 Blocks",
            poster = "/jVObyxtNxuPbG5czuKvm7pW56EV.jpg",
            backdrop = "/jEdce7g6VZHMoJ7DANX8NFQkVAW.jpg",
            year = "2017",
            overview = "Based in Neukölln, Berlin Toni manages the daily business of dealing with the Arabic gangs and ends up wanting to leave his old life behind for his family, but as expected, its never that simple."
        ),
        TVShow(
            id = 9,
            name = "Family Guy",
            poster =  "/gBGUL1UTUNmdRQT8gA1LUV4yg39.jpg",
            backdrop = "/tLCDKsXo6D84IVFanoElosSEKdp.jpg",
            year = "1999",
            overview = "Sick, twisted, politically incorrect and Freakin' Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he's not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues."
        )
    )

    fun getTVShows(): Observable<List<TVShow>> {

        return Observable.just(tvShowDummy)
            .delay(3, TimeUnit.SECONDS)
    }
}
package com.example.data

object DefaultBooksData {

    fun getDefaultBooks(): List<BookEntity> = listOf(
        BookEntity(
            id = "gatsby",
            title = "The Great Gatsby",
            author = "F. Scott Fitzgerald",
            genre = "Fiction",
            description = "A portrait of the Jazz Age in all its decadence and excess, following the mysterious Jay Gatsby and his obsessive love for Daisy Buchanan.",
            coverColorHex = "#1E3A8A", // Deep Navy Sapphire
            coverEmoji = "🍸",
            shelf = BookShelf.READING,
            isFavorite = true,
            currentChapterIndex = 0,
            scrollProgress = 0.15f,
            totalChapters = 3,
            wordsCount = 14200,
            estimatedMinutes = 55,
            lastReadTimestamp = System.currentTimeMillis() - 3600000L,
            rating = 5
        ),
        BookEntity(
            id = "pride_and_prejudice",
            title = "Pride and Prejudice",
            author = "Jane Austen",
            genre = "Classic Literature",
            description = "A sharp, witty comedy of manners capturing the tempestuous romance between the spirited Elizabeth Bennet and the proud Mr. Darcy.",
            coverColorHex = "#831843", // Velvet Plum
            coverEmoji = "👒",
            shelf = BookShelf.WANT_TO_READ,
            isFavorite = true,
            currentChapterIndex = 0,
            scrollProgress = 0f,
            totalChapters = 3,
            wordsCount = 16800,
            estimatedMinutes = 68,
            lastReadTimestamp = 0L,
            rating = 5
        ),
        BookEntity(
            id = "sherlock_holmes",
            title = "A Study in Scarlet",
            author = "Arthur Conan Doyle",
            genre = "Mystery",
            description = "The immortal tale introducing consulting detective Sherlock Holmes and Dr. John H. Watson as they unravel a perplexing murder in Victorian London.",
            coverColorHex = "#78350F", // Warm Amber Leather
            coverEmoji = "🔍",
            shelf = BookShelf.READING,
            isFavorite = false,
            currentChapterIndex = 1,
            scrollProgress = 0.42f,
            totalChapters = 2,
            wordsCount = 11500,
            estimatedMinutes = 45,
            lastReadTimestamp = System.currentTimeMillis() - 86400000L,
            rating = 4
        ),
        BookEntity(
            id = "alice_in_wonderland",
            title = "Alice in Wonderland",
            author = "Lewis Carroll",
            genre = "Fantasy",
            description = "A young girl named Alice tumbles down a rabbit hole into a nonsensical subterranean wonderland of eccentric creatures and philosophical riddles.",
            coverColorHex = "#065F46", // Emerald Forest
            coverEmoji = "🐇",
            shelf = BookShelf.FINISHED,
            isFavorite = true,
            currentChapterIndex = 1,
            scrollProgress = 1.0f,
            totalChapters = 2,
            wordsCount = 9800,
            estimatedMinutes = 38,
            lastReadTimestamp = System.currentTimeMillis() - 172800000L,
            rating = 5
        ),
        BookEntity(
            id = "metamorphosis",
            title = "The Metamorphosis",
            author = "Franz Kafka",
            genre = "Philosophy",
            description = "Gregor Samsa wakes one morning to discover he has transformed into an enormous monstrous insect, exploring isolation and existential absurdity.",
            coverColorHex = "#4C1D95", // Imperial Deep Purple
            coverEmoji = "🪲",
            shelf = BookShelf.WANT_TO_READ,
            isFavorite = false,
            currentChapterIndex = 0,
            scrollProgress = 0f,
            totalChapters = 2,
            wordsCount = 8400,
            estimatedMinutes = 32,
            lastReadTimestamp = 0L,
            rating = 4
        ),
        BookEntity(
            id = "art_of_war",
            title = "The Art of War",
            author = "Sun Tzu",
            genre = "Ancient Wisdom",
            description = "The definitive ancient military treatise on strategy, tactical deception, leadership psychology, and masterfully overcoming conflict.",
            coverColorHex = "#991B1B", // Crimson Dynasty
            coverEmoji = "⚔️",
            shelf = BookShelf.UNREAD,
            isFavorite = false,
            currentChapterIndex = 0,
            scrollProgress = 0f,
            totalChapters = 3,
            wordsCount = 7600,
            estimatedMinutes = 30,
            lastReadTimestamp = 0L,
            rating = 5
        ),
        BookEntity(
            id = "frankenstein",
            title = "Frankenstein",
            author = "Mary Shelley",
            genre = "Sci-Fi Classic",
            description = "The gothic masterpiece of Victor Frankenstein, a brilliant student who animates a creature born of graveyard remnants, only to abandon his creation.",
            coverColorHex = "#134E4A", // Dark Teal
            coverEmoji = "⚡",
            shelf = BookShelf.UNREAD,
            isFavorite = false,
            currentChapterIndex = 0,
            scrollProgress = 0f,
            totalChapters = 2,
            wordsCount = 10400,
            estimatedMinutes = 40,
            lastReadTimestamp = 0L,
            rating = 4
        )
    )

    fun getDefaultChapters(): List<ChapterEntity> = listOf(
        // The Great Gatsby
        ChapterEntity(
            id = "gatsby_0",
            bookId = "gatsby",
            chapterIndex = 0,
            title = "Chapter I: West Egg",
            content = """In my younger and more vulnerable years my father gave me some advice that I've been turning over in my mind ever since.

"Whenever you feel like criticizing any one," he told me, "just remember that all the people in this world haven't had the advantages that you've had."

He didn't say any more, but we've always been unusually communicative in a reserved way, and I understood that he meant a great deal more than that. In consequence, I'm inclined to reserve all judgements, a habit that has opened up many curious natures to me and also made me the victim of not a few veteran bores.

When I came back from the East last autumn I felt that I wanted the world to be in uniform and at a sort of moral attention forever; I wanted no more riotous excursions with privileged glimpses into the human heart. Only Gatsby, the man who gives his name to this book, was exempt from my reaction—Gatsby, who represented everything for which I have an unaffected scorn.

If personality is an unbroken series of successful gestures, then there was something gorgeous about him, some heightened sensitivity to the promises of life, as if he were related to one of those intricate machines that register earthquakes ten thousand miles away. This responsiveness had nothing to do with that flabby impressionability which is dignified under the name of the "creative temperament"—it was an extraordinary gift for hope, a romantic readiness such as I have never found in any other person and which it is not likely I shall ever find again.

I lived at West Egg, the—well, the less fashionable of the two, though this is a most superficial tag to express the bizarre and not a little sinister contrast between them. My house was at the very tip of the egg, only fifty yards from the Sound, and squeezed between two huge places that rented for twelve or fifteen thousand a season. The one on my right was a colossal affair by any standard—it was a factual imitation of some Hôtel de Ville in Normandy, with a tower on one side, spanking new under a thin beard of raw ivy, and a marble swimming pool, and more than forty acres of lawn and garden. It was Gatsby's mansion.

Across the courtesy bay the white palaces of fashionable East Egg glittered along the water, and the history of the summer really begins on the evening I drove over there to have dinner with the Tom Buchanans. Daisy was my second cousin once removed, and I'd known Tom in college. And just after the war I spent two days with them in Chicago.

Their house was even more elaborate than I expected, a cheerful red-and-white Georgian Colonial mansion, overlooking the bay. The lawn started at the beach and ran toward the front door for a quarter of a mile, jumping over sun-dials and brick walls and burning gardens.

Inside, the wind blew through the room, blew curtains in at one end and out the other like pale flags, twisting them up toward the frosted wedding-cake of the ceiling, and then rippled over the wine-colored rug, making a shadow on it as wind does on the sea.

The only completely stationary object in the room was an enormous couch on which two young women were buoyed up as though upon an anchored balloon. They were both in white, and their dresses were rippling and fluttering as if they had just been blown back in after a short flight around the house."""
        ),
        ChapterEntity(
            id = "gatsby_1",
            bookId = "gatsby",
            chapterIndex = 1,
            title = "Chapter II: The Valley of Ashes",
            content = """About half way between West Egg and New York the motor road hastily joins the railroad and runs beside it for a quarter of a mile, so as to shrink away from a certain desolate area of land. This is a valley of ashes—a fantastic farm where ashes grow like wheat into ridges and hills and grotesque gardens; where ashes take the forms of houses and chimneys and rising smoke and, finally, with a transcendent effort, of men who move dimly and already crumbling through the powdery air.

Occasionally a line of gray cars crawls along an invisible track, gives out a ghastly creak, and comes to rest, and immediately the ash-gray men swarm up with leaden spades and stir up an impenetrable cloud, which screens their obscure operations from your sight.

But above the gray land and the spasms of bleak dust which drift endlessly over it, you perceive, after a moment, the eyes of Doctor T. J. Eckleburg. The eyes of Doctor T. J. Eckleburg are blue and gigantic—their retinas are one yard high. They look out of no face, but, instead, from a pair of enormous yellow spectacles which pass over a non-existent nose.

Evidently some wild wag of an oculist set them there to fatten his practice in the borough of Queens, and then sank down himself into eternal blindness, or forgot them and moved away. But his eyes, dimmed a little by many painless days, under sun and rain, brood on over the solemn dumping ground.

The valley of ashes is bounded on one side by a small foul river, and, when the drawbridge is up to let barges through, the passengers on waiting trains can stare at the dismal scene for as long as half an hour. There is always a halt there of at least a minute, and it was because of this that I first met Tom Buchanan's mistress."""
        ),
        ChapterEntity(
            id = "gatsby_2",
            bookId = "gatsby",
            chapterIndex = 2,
            title = "Chapter III: The Great Parties",
            content = """There was music from my neighbor's house through the summer nights. In his blue gardens men and girls came and went like moths among the whisperings and the champagne and the stars. At high tide in the afternoon I watched his guests diving from the tower of his raft, or taking the sun on the hot sand of his beach while his two motor-boats slit the waters of the Sound, drawing aquaplanes over cataracts of foam.

On week-ends his Rolls-Royce became an omnibus, bearing parties to and from the city between nine in the morning and long past midnight, while his station wagon scampered like a brisk yellow bug to meet all trains. And on Mondays eight servants, including an extra gardener, toiled all day with mops and scrubbing-brushes and hammers and garden-shears, repairing the ravages of the night before.

Every Friday five crates of oranges and lemons arrived from a fruiterer in New York—every Monday these same oranges and lemons left his back door in a pyramid of pulpless halves. There was a machine in the kitchen which could extract the juice of two hundred oranges in half an hour if a little button was pressed two hundred times by a butler's thumb.

By seven o'clock the orchestra has arrived, no thin five-piece affair, but a whole pitful of oboes and trombones and saxophones and viols and cornets and piccolos, and low and high drums. The last swimmers have come in from the beach now and are dressing upstairs; the cars from New York are parked five deep in the drive, and already the halls and salons and verandas are gaudy with primary colors, and hair shorn in strange new ways, and shawls beyond the dreams of Castile.

The bar is in full swing, and floating rounds of cocktails permeate the garden outside, until the air is alive with chatter and laughter, and casual innuendo and introductions forgotten on the spot, and enthusiastic meetings between women who never knew each other's names."""
        ),

        // Pride and Prejudice
        ChapterEntity(
            id = "pride_and_prejudice_0",
            bookId = "pride_and_prejudice",
            chapterIndex = 0,
            title = "Chapter I: Netherfield Park",
            content = """It is a truth universally acknowledged, that a single man in possession of a good fortune, must be in want of a wife.

However little known the feelings or views of such a man may be on his first entering a neighbourhood, this truth is so well fixed in the minds of the surrounding families, that he is considered the rightful property of some one or other of their daughters.

"My dear Mr. Bennet," said his lady to him one day, "have you heard that Netherfield Park is let at last?"

Mr. Bennet replied that he had not.

"But it is," returned she; "for Mrs. Long has just been here, and she told me all about it."

Mr. Bennet made no answer.

"Do you not want to know who has taken it?" cried his wife impatiently.

"You want to tell me, and I have no objection to hearing it."

This was invitation enough.

"Why, my dear, you must know, Mrs. Long says that Netherfield is taken by a young man of large fortune from the north of England; that he came down on Monday in a chaise and four to see the place, and was so much delighted with it, that he agreed with Mr. Morris immediately; that he is to take possession before Michaelmas, and some of his servants are to be in the house by the end of next week."

"What is his name?"

"Bingley."

"Is he married or single?"

"Oh! Single, my dear, to be sure! A single man of large fortune; four or five thousand a year. What a fine thing for our girls!"

"How so? How can it affect them?"

"My dear Mr. Bennet," replied his wife, "how can you be so tiresome! You must know that I am thinking of his marrying one of them."""
        ),
        ChapterEntity(
            id = "pride_and_prejudice_1",
            bookId = "pride_and_prejudice",
            chapterIndex = 1,
            title = "Chapter II: The Visit",
            content = """Mr. Bennet was among the earliest of those who waited on Mr. Bingley. He had always intended to visit him, though to the last always assuring his wife that he should not go; and till the evening after the visit was paid she had no knowledge of it. It was then disclosed in the following manner. Observing his second daughter employed in trimming a hat, he suddenly addressed her with:

"I hope Mr. Bingley will like it, Lizzy."

"We are not in a way to know what Mr. Bingley likes," said her mother resentfully, "since we are not to visit."

"But you forget, mamma," said Elizabeth, "that we shall meet him at the assemblies, and that Mrs. Long promised to introduce him."

"I do not believe Mrs. Long will do any such thing. She has two nieces of her own. She is a selfish, hypocritical woman, and I have no opinion of her."

"No more have I," said Mr. Bennet; "and I am glad to find that you do not depend on her serving you."

Mrs. Bennet deigned not to make any reply, but, unable to contain herself, began scolding one of her daughters.

"Don't keep coughing so, Kitty, for Heaven's sake! Have a little compassion on my nerves. You tear them to pieces."

"Kitty has no discretion in her coughs," said her father; "she times them ill."

"I do not cough for my own amusement," replied Kitty fretfully. "When is your next ball to be, Lizzy?"""
        ),
        ChapterEntity(
            id = "pride_and_prejudice_2",
            bookId = "pride_and_prejudice",
            chapterIndex = 2,
            title = "Chapter III: The Ball at Meryton",
            content = """Not all that Mrs. Bennet, however, with the assistance of her five daughters, could ask on the subject, was sufficient to draw from her husband any satisfactory description of Mr. Bingley. They attacked him in various ways—with barefaced questions, ingenious suppositions, and distant surmises; but he eluded the skill of them all, and they were at last obliged to accept the second-hand intelligence of their neighbour, Lady Lucas.

Her report was highly favourable. Sir William had been delighted with him. He was quite young, wonderfully handsome, extremely agreeable, and, to crown the whole, he meant to be at the next assembly with a large party. Nothing could be more delightful! To be fond of dancing was a certain step towards falling in love; and very lively hopes of Mr. Bingley's heart were entertained.

Mr. Bingley was good-looking and gentlemanlike; he had a pleasant countenance, and easy, unaffected manners. His sisters were fine women, with an air of decided fashion. His brother-in-law, Mr. Hurst, merely looked the gentleman; but his friend Mr. Darcy soon drew the attention of the room by his fine, tall person, handsome features, noble mien, and the report which was in general circulation within five minutes after his entrance, of his having ten thousand a year.

The gentlemen pronounced him to be a fine figure of a man, the ladies declared he was much handsomer than Mr. Bingley, and he was looked at with great admiration for about half the evening, till his manners gave a disgust which turned the tide of his popularity; for he was discovered to be proud; to be above his company, and above being pleased; and not all his large estate in Derbyshire could then save him from having a most forbidding, disagreeable countenance, and being unworthy to be compared with his friend."""
        ),

        // Sherlock Holmes: A Study in Scarlet
        ChapterEntity(
            id = "sherlock_holmes_0",
            bookId = "sherlock_holmes",
            chapterIndex = 0,
            title = "Chapter I: Mr. Sherlock Holmes",
            content = """In the year 1878 I took my degree of Doctor of Medicine of the University of London, and proceeded to Netley to go through the course prescribed for surgeons in the army. Having completed my studies there, I was duly attached to the Fifth Northumberland Fusiliers as Assistant Surgeon. The regiment was stationed in India at the time, and before I could reach it, the second Afghan war had broken out.

On landing at Bombay, I learned that my corps had advanced through the passes, and was already deep in the enemy's country. I followed, however, with many other officers which were in similar condition, and succeeded in reaching Candahar in safety, where I found my regiment, and at once entered upon my new duties.

The campaign brought honours and promotion to many, but for me it had nothing but misfortune and disaster. I was removed from my brigade and attached to the Berkshires, with whom I served at the fatal battle of Maiwand. There I was struck on the shoulder by a Jezail bullet, which shattered the bone and grazed the subclavian artery. I should have fallen into the hands of the murderous Ghazis had it not been for the devotion and courage shown by Murray, my orderly, who threw me across a pack-horse, and succeeded in bringing me safely to the British lines.

Worn with pain, and weak from the prolonged hardships which I had undergone, I was removed to the base hospital at Peshawur. Here I rallied, and had already improved so far as to be able to walk about the wards, and even to bask a little upon the verandah, when I was struck down by enteric fever, that curse of our Indian possessions. For months my life was despaired of, and when at last I came to myself and became convalescent, I was so weak and emaciated that a medical board determined that not a day should be lost in sending me back to England.

Under such circumstances I naturally gravitated to London, that great cesspool into which all the loungers and idlers of the Empire are irresistibly drained."""
        ),
        ChapterEntity(
            id = "sherlock_holmes_1",
            bookId = "sherlock_holmes",
            chapterIndex = 1,
            title = "Chapter II: The Science of Deduction",
            content = """We met next day as he had arranged, and inspected the rooms at No. 221B, Baker Street, of which he had spoken at our meeting. They consisted of two comfortable bedrooms and a single large airy sitting-room, cheerfully furnished, and illuminated by two broad windows. So desirable in every way were the apartments, and so moderate did the terms seem when divided between us, that the bargain was concluded upon the spot, and we at once entered into possession.

We took up our abode in Baker Street the same evening, and my companion immediately proceeded to unpack his various cases and portmanteaus. I had certainly no cause to complain of his conduct. He was quiet in his ways, his habits were regular, and it was rare that he was up after ten at night. Sometimes he spent his days at the chemical laboratory, sometimes in the dissecting-rooms, and occasionally in long walks, which appeared to take him into the lowest portions of the City.

His energy was an astonishment to me. Nothing could exceed his activity when the working fit was upon him; but now and again a reaction would seize him, and for days on end he would lie upon the sofa in the sitting-room, hardly uttering a word or moving a muscle from morning to night. On these occasions I have noticed such a dreamy, vacant expression in his eyes, that I might have suspected him of being addicted to the use of some narcotic, had not the temperance and cleanliness of his whole life forbidden such a notion.

His ignorance was as remarkable as his knowledge. Of contemporary literature, of philosophy and politics he appeared to know next to nothing. Upon my quoting Thomas Carlyle, he inquired in the naivest way who he might be and what he had done.

My surprise reached a climax, however, when I found incidentally that he was ignorant of the Copernican Theory and of the composition of the Solar System. That any civilized human being in this nineteenth century should not be aware that the earth traveled round the sun appeared to be a fact which I could scarcely comprehend."""
        ),

        // Alice in Wonderland
        ChapterEntity(
            id = "alice_in_wonderland_0",
            bookId = "alice_in_wonderland",
            chapterIndex = 0,
            title = "Chapter I: Down the Rabbit-Hole",
            content = """Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do: once or twice she had peeped into the book her sister was reading, but it had no pictures or conversations in it, "and what is the use of a book," thought Alice, "without pictures or conversations?"

So she was considering in her own mind (as well as she could, for the hot day made her feel very sleepy and stupid), whether the pleasure of making a daisy-chain would be worth the trouble of getting up and picking the daisies, when suddenly a White Rabbit with pink eyes ran close by her.

There was nothing so very remarkable in that; nor did Alice think it so very much out of the way to hear the Rabbit say to itself, "Oh dear! Oh dear! I shall be late!" (when she thought it over afterwards, it occurred to her that she ought to have wondered at this, but at the time it all seemed quite natural); but when the Rabbit actually took a watch out of its waistcoat-pocket, and looked at it, and then hurried on, Alice started to her feet, for it flashed across her mind that she had never before seen a rabbit with either a waistcoat-pocket, or a watch to take out of it, and burning with curiosity, she ran across the field after it, and fortunately was just in time to see it pop down a large rabbit-hole under the hedge.

In another moment down went Alice after it, never once considering how in the world she was to get out again.

The rabbit-hole went straight on like a tunnel for some way, and then dipped suddenly down, so suddenly that Alice had not a moment to think about stopping herself before she found herself falling down a very deep well."""
        ),
        ChapterEntity(
            id = "alice_in_wonderland_1",
            bookId = "alice_in_wonderland",
            chapterIndex = 1,
            title = "Chapter II: The Pool of Tears",
            content = """"Curiouser and curiouser!" cried Alice (she was so much surprised, that for the moment she quite forgot how to speak good English); "now I'm opening out like the largest telescope that ever was! Good-bye, feet!" (for when she looked down at her feet, they seemed to be almost out of sight, they were getting so far off). "Oh, my poor little feet, I wonder who will put on your shoes and stockings for you now, dears? I'm sure I shan't be able! I shall be a great deal too far off to trouble myself about you: you must manage the best way you can;—but I must be kind to them," thought Alice, "or perhaps they won't walk the way I want to go! Let me see: I'll give them a new pair of boots every Christmas."

And she went on planning to herself how she would manage it. "They must go by the carrier," she thought; "and how funny it'll seem, sending presents to one's own feet! And how odd the directions will look!

Alice's Right Foot, Esq.
Hearthrug,
near the Fender,
(with Alice's love).

Oh dear, what nonsense I'm talking!"

Just then her head struck against the roof of the hall: in fact she was now more than nine feet high, and she at once took up the little golden key and hurried off to the garden door."""
        ),

        // The Metamorphosis
        ChapterEntity(
            id = "metamorphosis_0",
            bookId = "metamorphosis",
            chapterIndex = 0,
            title = "Chapter I: The Awakening",
            content = """One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arched calluses into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off any moment. His many legs, pitifully thin compared with the size of the rest of him, waved about helplessly as he looked.

"What's happened to me?" he thought. It wasn't a dream. His room, a proper human room although a little too small, lay peacefully between its four familiar walls. A collection of textile samples lay spread out on the table—Samsa was a travelling salesman—and above it there hung a picture that he had recently cut out of an illustrated magazine and housed in a nice, gilded frame. It showed a lady fitted out with a fur hat and fur boa who sat upright, raising a heavy fur muff that covered the whole of her lower arm towards the viewer.

Gregor then turned to look out the window at the dull weather. Drops of rain could be heard hitting the pane, which made him feel quite sad. "How about if I sleep a little bit longer and forget all this nonsense", he thought, but that was something he was unable to do because he was used to sleeping on his right, and in his present state couldn't get into that position. However hard he threw himself onto his right, he always rolled back to where he was."""
        ),
        ChapterEntity(
            id = "metamorphosis_1",
            bookId = "metamorphosis",
            chapterIndex = 1,
            title = "Chapter II: The Family",
            content = """It was not until dusk that Gregor awoke from a deep and heavy slumber. He would certainly not have stayed asleep much longer even without being disturbed, as he felt he had rested and slept enough, but he had the impression that a hurried step and a light shutting of the door to the hall had awakened him. The light from the electric street lamps shone here and there in pale patches on the ceiling and the upper parts of the furniture, but down where Gregor was it was dark.

Slowly, awkwardly feeling his way with his antennae, which he only now began to appreciate, he pushed himself over to the door to see what had been happening there. His left side seemed like one single long, unpleasantly taut scar, and he really had to limp on his two rows of legs. One little leg had moreover been seriously injured in the course of the morning—it was nearly a miracle that only one had been injured—and crawled helplessly behind.

Not until he got to the door did he discover what had actually drawn him there: it was the smell of something to eat. There stood a bowl filled with fresh milk in which small slices of white bread were floating. He almost laughed with joy, as he was now even hungrier than he had been in the morning, and immediately dipped his head into the milk, almost up over his eyes."""
        ),

        // The Art of War
        ChapterEntity(
            id = "art_of_war_0",
            bookId = "art_of_war",
            chapterIndex = 0,
            title = "Chapter I: Laying Plans",
            content = """Sun Tzu said: The art of war is of vital importance to the State. It is a matter of life and death, a road either to safety or to ruin. Hence it is a subject of inquiry which can on no account be neglected.

The art of war, then, is governed by five constant factors, to be taken into account in one's deliberations, when seeking to determine the conditions obtaining in the field. These are:
(1) The Moral Law;
(2) Heaven;
(3) Earth;
(4) The Commander;
(5) Method and discipline.

The Moral Law causes the people to be in complete accord with their ruler, so that they will follow him regardless of their lives, undismayed by any danger.

Heaven signifies night and day, cold and heat, times and seasons.

Earth comprises distances, great and small; danger and security; open ground and narrow passes; the chances of life and death.

The Commander stands for the virtues of wisdom, sincerely, benevolence, strictness and courage.

By Method and discipline are to be understood the marshaling of the army in its proper subdivisions, the graduations of rank among the officers, the maintenance of roads by which supplies may reach the army, and the control of military expenditure.

These five heads should be familiar to every general: he who knows them will be victorious; he who knows them not will fail."""
        ),
        ChapterEntity(
            id = "art_of_war_1",
            bookId = "art_of_war",
            chapterIndex = 1,
            title = "Chapter II: Waging War",
            content = """Sun Tzu said: In the operations of war, where there are in the field a thousand swift chariots, as many heavy chariots, and a hundred thousand mail-clad soldiers, with provisions enough to carry them a thousand li, the expenditure at home and at the front, including entertainment of guests, small items such as glue and paint, and sums spent on chariots and armor, will reach the total of a thousand ounces of silver per day. Such is the cost of raising an army of 100,000 men.

When you engage in actual fighting, if victory is long in coming, then men's weapons will grow dull and their ardor will be damped. If you lay siege to a town, you will exhaust your strength.

Again, if the campaign is protracted, the resources of the State will not be equal to the strain.

Now, when your weapons are dulled, your ardor damped, your strength exhausted and your treasure spent, other chieftains will spring up to take advantage of your extremity. Then no man, however wise, will be able to avert the consequences that must ensue.

Thus, though we have heard of stupid haste in war, cleverness has never been seen associated with long delays.

There is no instance of a country having benefited from prolonged warfare."""
        ),
        ChapterEntity(
            id = "art_of_war_2",
            bookId = "art_of_war",
            chapterIndex = 2,
            title = "Chapter III: Attack by Stratagem",
            content = """Sun Tzu said: In the practical art of war, the best thing of all is to take the enemy's country whole and intact; to shatter and destroy it is not so good. So, too, it is better to recapture an army entire than to destroy it, to capture a regiment, a detachment or a company entire than to destroy them.

Hence to fight and conquer in all your battles is not supreme excellence; supreme excellence consists in breaking the enemy's resistance without fighting.

Thus the highest form of generalship is to balk the enemy's plans; the next best is to prevent the junction of the enemy's forces; the next in order is to attack the enemy's army in the field; and the worst policy of all is to besiege walled cities.

The rule is, not to besiege walled cities if it can possibly be avoided. The preparation of mantlets, movable shelters, and various engines of war, will take up three full months; and the piling up of mounds over against the walls will take three months more.

The general, unable to control his irritation, will launch his men to the assault like swarming ants, with the result that one-third of his men are slain, while the town still remains untaken. Such are the disastrous effects of a siege.

Therefore the skillful leader subdues the enemy's troops without any fighting; he captures their cities without laying siege to them; he overthrows their kingdom without lengthy operations in the field."""
        ),

        // Frankenstein
        ChapterEntity(
            id = "frankenstein_0",
            bookId = "frankenstein",
            chapterIndex = 0,
            title = "Chapter I: Genevese Heritage",
            content = """I am by birth a Genevese, and my family is one of the most distinguished of that republic. My ancestors had been for many years counsellors and syndics, and my father had filled several public situations with honour and reputation. He was respected by all who knew him for his integrity and indefatigable attention to public business. He passed his younger days perpetually occupied by the affairs of his country; nor until the decline of life did he think of marrying, and bestowing on the state sons who might carry his virtues and his name down to posterity.

As the circumstances of his marriage illustrate his character, I cannot refrain from relating them. One of his most intimate friends was a merchant who, from a flourishing state, fell, through numerous mischances, into poverty. This man, whose name was Beaufort, was of a proud and unbending disposition and could not bear to live in poverty and oblivion in the same country where he had once been distinguished for his rank and magnificence. Having paid his debts, therefore, in the most honourable manner, he retreated with his daughter to the town of Lucerne, where he lived unknown and in wretchedness.

My father loved Beaufort with the truest friendship and was deeply grieved by his retreat in these unfortunate circumstances. He bitterly deplored the false pride which led his friend to a conduct so little worthy of the affection that united them. He lost no time in endeavouring to seek him out, with the hope of persuading him to begin the world again through his credit and assistance."""
        ),
        ChapterEntity(
            id = "frankenstein_1",
            bookId = "frankenstein",
            chapterIndex = 1,
            title = "Chapter II: The Spark of Being",
            content = """It was on a dreary night of November that I beheld the accomplishment of my toils. With an anxiety that almost amounted to agony, I collected the instruments of life around me, that I might infuse a spark of being into the lifeless thing that lay at my feet. It was already one in the morning; the rain pattered dismally against the panes, and my candle was nearly burnt out, when, by the glimmer of the half-extinguished light, I saw the dull yellow eye of the creature open; it breathed hard, and a convulsive motion agitated its limbs.

How can I describe my emotions at this catastrophe, or how delineate the wretch whom with such infinite pains and care I had endeavoured to form? His limbs were in proportion, and I had selected his features as beautiful. Beautiful! Great God! His yellow skin scarcely covered the work of muscles and arteries beneath; his hair was of a lustrous black, and flowing; his teeth of a pearly whiteness; but these luxuriances only formed a more horrid contrast with his watery eyes, that seemed almost of the same colour as the dun-white sockets in which they were set, his shrivelled complexion and straight black lips.

The different accidents of life are not so changeable as the feelings of human nature. I had worked hard for nearly two years, for the sole purpose of infusing life into an inanimate body. For this I had deprived myself of rest and health. I had desired it with an ardour that far exceeded moderation; but now that I had finished, the beauty of the dream vanished, and breathless horror and disgust filled my heart."""
        )
    )
}

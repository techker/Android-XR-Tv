package com.techker.tvvr.data

object MockData {

    //Channels data
    //createProgram("Early Show", 0, 0, 6, 0),
    //Show title
    //Start time 0-1-2-3-4-5..to 24   1 is 1AM 13 is 1PM
    //Start Minutes 0 or 30
    //End Hour 0-1-2-3-4-5..to 24
    //End Minutes 0 or 30
    //Program Description
    //Program Poster
    val channels =
        listOf(
            Channel(
                id = "1",
                name = "Channel 1",
                logo = "https://raw.githubusercontent.com/Jasmeet181/mediaportal-us-logos/master/TV/.Light/ESPN%20HD.png",
                programs = fillProgramGaps(
                    listOf(
                        createProgram("Early Show", 0, 0, 6, 0),
                        createProgram("Morning News", 6, 0, 8, 30),
                        createProgram("Talk Show", 8, 30, 10, 30),
                        createProgram("Movie", 10, 30, 13, 30, isRecording = true),
                        createProgram("News", 13, 30, 15, 30),
                        createProgram("Series", 15, 30, 18, 0),
                        createProgram("Evening News", 18, 0, 20, 0),
                        createProgram("Prime Time", 20, 0, 23, 0),
                        createProgram("Late Show", 23, 0, 0, 0),
                    )
                )
            ),
            Channel(
                id = "2",
                name = "Channel 2",
                logo = "https://raw.githubusercontent.com/Jasmeet181/mediaportal-us-logos/master/TV/.Light/MTV.png",
                programs = listOf(
                    createProgram("Early Show", 0, 0, 2, 30),
                    createProgram("Morning News", 2, 30, 3, 30),
                    createProgram("Talk Show", 3, 30, 4, 0),
                    createProgram("Movie", 4, 0, 5, 30, isRecording = true),
                    createProgram("Series", 5, 30, 8, 0),
                    createProgram("Evening News", 8, 0, 10, 0),
                    createProgram("Prime Time", 10, 0, 11, 0),
                    createProgram("Late Show", 11, 0, 12, 30),
                    createProgram("Chicago PD", 12, 30, 13, 30),
                    createProgram("DOC", 13, 30, 14, 30),
                    createProgram("STAT", 14, 30, 15, 30),
                    createProgram("CROWN", 15, 30, 16, 0),
                    createProgram("Series", 16, 0, 18, 0),
                    createProgram("Evening News", 18, 0, 20, 0),
                    createProgram("Prime Time", 20, 0, 23, 0),
                    createProgram("Late Show", 23, 30, 0, 30),
                )
            ),
            Channel(
                id = "3",
                name = "Channel 3",
                logo = "https://ott-logos.s3.us-east-1.amazonaws.com/WGNHD.png",
                programs = listOf(
                    createProgram("Early Show", 0, 0, 1, 30),
                    createProgram("Morning News", 1, 30, 3, 0),
                    createProgram("Talk Show", 3, 0, 4, 0),
                    createProgram("Movie", 4, 0, 5, 30, isRecording = true),
                    createProgram("Series", 5, 30, 8, 0),
                    createProgram("Evening News", 8, 0, 10, 0),
                    createProgram("Prime Time", 10, 0, 11, 0),
                    createProgram("Late Show", 11, 0, 12, 30),
                    createProgram("Chicago PD", 12, 30, 13, 30),
                    createProgram("DOC", 13, 30, 14, 30),
                    createProgram("STAT", 14, 30, 15, 30),
                    createProgram("CROWN", 15, 30, 16, 0),
                    createProgram("Series", 16, 0, 18, 0),
                    createProgram("Evening News", 18, 0, 20, 30),
                    createProgram("Prime Time", 20, 30, 23, 0),
                    createProgram("Late Show", 23, 30, 0, 0),
                )
            ),
            Channel(
                id = "4",
                name = "Channel 4",
                logo = "https://ott-logos.s3.us-east-1.amazonaws.com/BBCHD.png",
                programs = listOf(
                    createProgram("Early Show", 0, 0, 1, 0),
                    createProgram("Morning News", 1, 0, 2, 0),
                    createProgram("Talk Show", 2, 0, 3, 0),
                    createProgram("Movie", 3, 0, 4, 0, isRecording = true),
                    createProgram("News", 4, 0, 5, 0),
                    createProgram("Series", 5, 0, 8, 0),
                    createProgram("Evening News", 8, 0, 10, 0),
                    createProgram("Prime Time", 10, 0, 11, 0),
                    createProgram("Late Show", 11, 0, 12, 30),
                    createProgram("Chicago PD", 12, 30, 13, 30),
                    createProgram("DOC", 13, 30, 14, 30),
                    createProgram("STAT", 14, 30, 15, 30),
                    createProgram("CROWN", 15, 30, 16, 0),
                    createProgram("True Crime", 16, 0, 17, 0),
                    createProgram("Paid Programing", 17, 0, 18, 0),
                    createProgram("Local News", 18, 0, 19, 0),
                    createProgram("Show", 19, 0, 20, 0),
                    createProgram("Show", 20, 0, 21, 0),
                    createProgram("Chicago PD", 21, 0, 22, 0),
                    createProgram("Chicago Fire", 22, 0, 23, 0),
                    createProgram("Matlock", 23, 0, 0, 0),
                )
            ),
            Channel(
                id = "5",
                name = "Channel 5",
                logo = "https://ott-logos.s3.us-east-1.amazonaws.com/BETHD.png",
                programs = listOf(
                    createProgram("Early Show", 0, 0, 2, 30),
                    createProgram("Morning News", 2, 30, 3, 30),
                    createProgram("Talk Show", 3, 30, 4, 0),
                    createProgram("Movie", 4, 0, 5, 30, isRecording = true),
                    createProgram("Series", 5, 30, 8, 0),
                    createProgram("Evening News", 8, 0, 10, 0),
                    createProgram("Prime Time", 10, 0, 11, 0),
                    createProgram("Late Show", 11, 0, 12, 30),
                    createProgram("Chicago PD", 12, 30, 13, 30),
                    createProgram("DOC", 13, 30, 14, 30),
                    createProgram("STAT", 14, 30, 15, 30),
                    createProgram("CROWN", 15, 30, 16, 0),
                    createProgram("Series", 16, 0, 18, 0),
                    createProgram("Evening News", 18, 0, 20, 0),
                    createProgram("Prime Time", 20, 0, 23, 0),
                    createProgram("Late Show", 23, 0, 0, 0),
                )
            ),
            Channel(
                id = "6",
                name = "Channel 6",
                logo = "https://github.com/Jasmeet181/mediaportal-us-logos/blob/master/TV/.Light/CHCH%20TV.png?raw=true",
                programs = listOf(
                    createProgram("Early Show", 0, 0, 1, 30),
                    createProgram("Morning News", 1, 30, 2, 30),
                    createProgram("Talk Show", 2, 30, 3, 0),
                    createProgram("Movie", 3, 0, 4, 0, isRecording = true),
                    createProgram("Movie", 4, 0, 5, 30, isRecording = true),
                    createProgram("Series", 5, 30, 8, 0),
                    createProgram("Evening News", 8, 0, 10, 0),
                    createProgram("Prime Time", 10, 0, 11, 0),
                    createProgram("Late Show", 11, 0, 12, 30),
                    createProgram("Chicago PD", 12, 30, 13, 30),
                    createProgram("DOC", 13, 30, 14, 30),
                    createProgram("STAT", 14, 30, 15, 30),
                    createProgram("CROWN", 15, 30, 16, 0),
                    createProgram("Series", 16, 0, 18, 0),
                    createProgram("Evening News", 18, 0, 20, 0),
                    createProgram("Prime Time", 20, 0, 23, 0),
                    createProgram("Late Show", 23, 0, 0, 0),
                )
            ),
            Channel(
                id = "7",
                name = "Channel 7",
                logo = "https://raw.githubusercontent.com/Jasmeet181/mediaportal-us-logos/master/TV/.Light/Discovery.png",
                programs = fillProgramGaps(
                    listOf(
                        createProgram("Early Show", 0, 0, 2, 30),
                        createProgram("Morning News", 2, 30, 3, 30),
                        createProgram("Talk Show", 3, 30, 4, 0),
                        createProgram("Movie", 4, 0, 5, 30, isRecording = true),
                        createProgram("News", 5, 30, 16, 0),
                        createProgram("Series", 16, 0, 18, 0),
                        createProgram("Evening News", 18, 0, 20, 0),
                        createProgram("Prime Time", 20, 0, 23, 0),
                        createProgram("Late Show", 23, 30, 0, 0),
                    )
                )
            ),
            Channel(
                id = "8",
                name = "Channel 8",
                logo = "https://raw.githubusercontent.com/Jasmeet181/mediaportal-us-logos/master/TV/.Light/History%20HD.png",
                programs = fillProgramGaps(
                    listOf(
                        createProgram("Early Show", 0, 0, 2, 30),
                        createProgram("Morning News", 2, 30, 3, 30),
                        createProgram("Talk Show", 3, 30, 4, 0),
                        createProgram("Movie", 4, 0, 4, 30, isRecording = true),
                        createProgram("News", 4, 30, 16, 0),
                        createProgram("Series", 16, 0, 18, 0),
                        createProgram("Evening News", 18, 0, 20, 0),
                        createProgram("Prime Time", 20, 0, 23, 0),
                        createProgram("Late Show", 23, 30, 0, 0),
                    )
                )
            ),
            Channel(
                id = "9",
                name = "Channel 9",
                logo = "https://raw.githubusercontent.com/Jasmeet181/mediaportal-us-logos/master/TV/.Light/Discovery%20Life.png",
                programs = fillProgramGaps(
                    listOf(
                        createProgram("Early Show", 0, 0, 2, 30),
                        createProgram("Morning News", 2, 30, 3, 30),
                        createProgram("Talk Show", 3, 30, 4, 0),
                        createProgram("Movie", 4, 0, 5, 0, isRecording = true),
                        createProgram("News", 5, 0, 16, 0),
                        createProgram("Series", 16, 0, 18, 0),
                        createProgram("Evening News", 18, 0, 20, 0),
                        createProgram("Prime Time", 20, 0, 23, 0),
                        createProgram("Late Show", 23, 30, 0, 0),
                    )
                )
            )
        )
//https://via.placeholder.com/200x300
    val sampleMovies = listOf(
        Movie("1", "https://blog.icons8.com/wp-content/uploads/old-uploads/2019/05/poster-for-movie.png", "Movie 1", "Description"),
        Movie("2", "https://lh3.googleusercontent.com/proxy/YFn3AAC_N_EcWBUODxGNyQpG6Kef8lqoonOLHsL7n7h-Bqsj8FWNEnGeDuBT5r38ITnMHNvVbIGpyfXRPXtQ4p_R5Bhjlg", "Movie 2", "Description"),
        Movie("3", "https://athenaposters.ca/wp-content/uploads/2019/09/SBP9839-E.T.-Movie-Poster-300x448.jpg", "Movie 3", "Description"),
        Movie("4", "https://images.squarespace-cdn.com/content/v1/5a7f41ad8fd4d236a4ad76d0/1669842708561-ZGO3HUU3P9F24PKN1V6I/JungleBook_023B_ResUp_DC_v02logo2k.jpg", "Movie 4", "Description"),
        Movie("5", "https://rukminim2.flixcart.com/image/850/1000/jnc2bgw0/poster/y/b/3/medium-hollywood-movie-wall-poster-the-dark-tower-hd-quality-original-imafayzxa7v4nh4s.jpeg?q=90&crop=false", "Movie 5", "Description"),
        Movie("6", "https://blogarchive.goodillustration.com/wp-content/uploads/2023/01/640-4.jpg", "Movie 6", "Description"),
        Movie("7", "https://i.pinimg.com/736x/db/12/82/db1282e0f4a2ca5730037a3669b6ddd6.jpg", "Movie 7", "Description"),
        Movie("8", "https://hips.hearstapps.com/hmg-prod/images/1988-beetlejuice-1525959351.png?crop=1xw:1xh;center,top&resize=980:*", "Movie 8", "Description"),
        Movie("9", "https://akm-img-a-in.tosshub.com/indiatoday/inline-images/GD8OhxNbcAAeo3o.jpg?VersionId=ZLxtIDN0p7stZbaw1oOX6vkBLUjvg8CQ&size=750:*", "Movie 9", "Description"),
        Movie("10", "https://yc.cldmlk.com/8pqs2df0vwxtfv9jwgzxeb5efm/uploads/vertical_12ac5bf3-a5d9-4f43-8777-8210cce8c236.jpg", "Movie 10", "Description"),

    )

    val homePageCarouselItems = mutableListOf(
        Movie("1", "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/89058/93685/Joker-2019-Final-Style-steps-Poster-buy-original-movie-posters-at-starstills__62518.1669120603.jpg?c=2", "Joker", "2009"),
        Movie("2", "https://i.ebayimg.com/images/g/x-UAAOSw4upkQU4s/s-l1200.jpg", "Star Wars", "The Empire strikes back"),
        Movie("3", "https://cdn.shopify.com/s/files/1/0830/9575/files/dune-part-two-movie-poster-matt-ferguson_ac86f8c9-f410-450c-805b-c4352aac4a55_540x.jpg?v=1730814717", "Dune 2", "Part 2"),
        Movie("4", "https://cdn.prod.website-files.com/6009ec8cda7f305645c9d91b/66a4263d01a185d5ea22eeda_6408f76710a9935109f855d4_smile-min.png", "Smile", "Horror"),
        Movie("5", "https://i.ebayimg.com/images/g/9PcAAOSwIpFhz5a3/s-l1200.jpg", "The Batman", "Robert Pattinson"),
    )

    val recentChannels = listOf(
        channels
    )

    /**
     * Functions for EPG
     */

    fun timeToFloat(hour: Int, minute: Int): Float {
        return hour + (minute / 60f)
    }

    fun calculateProgramValues(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int): Pair<Float, Float> {
        val startTimeFloat = timeToFloat(startHour, startMinute)
        val endTimeFloat = timeToFloat(endHour, endMinute)

        // Handle cases where program ends next day
        val duration = if (endTimeFloat <= startTimeFloat) {
            (24f - startTimeFloat) + endTimeFloat
        } else {
            endTimeFloat - startTimeFloat
        }

        return Pair(startTimeFloat, duration)
    }

    fun createProgram(
        title: String,
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
        isRecording: Boolean = false
    ): Program {
        val (startTime, duration) = calculateProgramValues(startHour, startMinute, endHour, endMinute)
        return Program(title, startTime, duration, isRecording)
    }

    // function to fill gaps in program schedule
    fun fillProgramGaps(programs: List<Program>): List<Program> {
        val sortedPrograms = programs.sortedBy { it.startTime }
        val filledPrograms = mutableListOf<Program>()
        var currentTime = 0f

        sortedPrograms.forEach { program ->
            // If there's a gap before the program, fill it
            if (program.startTime > currentTime) {
                filledPrograms.add(createEmptyProgram(currentTime, program.startTime - currentTime))
            }
            filledPrograms.add(program)
            currentTime = program.startTime + program.duration
        }

        // Fill gap at the end if needed (up to 24 hours)
        if (currentTime < 24f) {
            filledPrograms.add(createEmptyProgram(currentTime, 24f - currentTime))
        }

        return filledPrograms
    }

    // function to create empty program slots
    fun createEmptyProgram(startTime: Float, duration: Float): Program {
        return Program("No Program", startTime, duration)
    }

}
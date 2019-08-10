package com.acdprd.testcontent

import su.bnet.utils.extensions.firstCharToUpper
import su.bnet.utils.extensions.nextChar
import su.bnet.utils.extensions.nextCharRu
import su.bnet.utils.extensions.randomElement
import kotlin.random.Random

object TestInstance {

    object Reuse {
        fun randomWords(
            count: Int,
            english: Boolean = false,
            firstCharUpperCase: Boolean = false
        ): String {
            return StringBuilder().apply {
                for (i in 0 until count) {
                    append(randomWord(english = english, firstCharUpperCase = firstCharUpperCase))
                    append(" ")
                }
            }.toString()
        }

        fun randomWord(
            offset: Int = 2,
            english: Boolean = false,
            firstCharUpperCase: Boolean = false
        ): String {
            val l = Random.nextInt(5) + offset

            var sb = StringBuilder().apply {
                for (i in 0 until l) {
                    if (english) append(Random.nextChar(randomUppercase = false))
                    else append(Random.nextCharRu(false))
                }

            }.toString()

            if (firstCharUpperCase) sb = sb.firstCharToUpper()
            return sb
        }

        private fun getImages(): MutableList<String> {
            return mutableListOf(
                "http://en.es-static.us/upl/2016/03/black-hole-large-megallanic-cloud.jpg",
                "http://cdn.sci-news.com/images/enlarge4/image_5945e-Wandering-Supermassive-Black-Hole.jpg",
                "https://www.dailygalaxy.com/wp-content/images/6a00d8341bf7f753ef01bb0915d1ca970d.jpg?x14277",
                "http://www.abc.net.au/news/image/8253538-3x2-940x627.jpg",
                "https://ufoholic.com/wp-content/uploads/2016/06/black-hole-2.jpg",
                "https://cdn.pixabay.com/photo/2017/04/22/00/14/universe-2250310_960_720.jpg",
                "https://www.eusemfronteiras.com.br/wp-content/uploads/2017/03/bigstock-153262058-810x459.jpg",
                "http://ploughshares.ca/wp-content/uploads/2017/12/milkyway.jpeg",
                "https://i.ytimg.com/vi/jqMmW2mhAhY/maxresdefault.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwKm0KOK7S4aqhH9tqIBqpCmBj8OgqLhGJyW6JqkgVsvyOJdt3GQ",
                "https://3c1703fe8d.site.internapcdn.net/newman/gfx/news/hires/2017/neutrinofaci.jpg",
                "https://wptschedule.org/ulphoto/wpt_1524165618.jpg",
                "http://dailygalaxy.com/wp-content/uploads/2019/05/191749_web-1-1024x619-1024x619.jpg",
                "https://scitechdaily.com/images/Gravitational-Waves-Reveal-How-Fast-Our-Universe-is-Expanding.jpg",
                "https://www.popsci.com/sites/popsci.com/files/styles/1000_1x_/public/import/2013/images/2012/04/PSC0512_FY_038.jpg?itok=c4X0Hwbc",
                "https://cdn.newsapi.com.au/image/v1/9fdbf585d17c95f7a31ccacdb6466af9",
                "https://images.newscientist.com/wp-content/uploads/2016/02/black-hole-jet-star.jpg",
                "https://cdn.images.express.co.uk/img/dynamic/151/590x/black-hole-1009631.jpg?r=1535473319742",
                "https://akm-img-a-in.tosshub.com/indiatoday/images/story/201805/GettyImages-724233205.jpeg?CF8d_NnO23E_MZOyhex1hLL4Gnz1WHw7",
                "https://s3.amazonaws.com/cdn.ideastations.org/article-images/article-width/nova-black-holes-image37.jpg",
                "http://www.erzincanyasam.com/wp-content/uploads/2015/07/karadelikler.jpg"
            )
        }

        fun randomImageUrl(): String {
            return getImages().randomElement()
        }

        fun randomImages(count: Int): List<String> {
            val res = mutableListOf<String>()
            val from = getImages()

            for (i in 0.rangeTo(count)) {
                if (from.isEmpty()) return res
                val index = Random.nextInt(from.size)
                res.add(from[index])
                from.removeAt(index)
            }

            return res
        }

        fun randomNumbersAsString(length: Int): String {
            val ssb = StringBuilder()

            for (i in 0 until length) {
                ssb.append(Random.nextInt(10))
            }

            return ssb.toString()
        }
    }
}
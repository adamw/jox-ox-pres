package pres

import ox.channels.Source
import ox.supervised

import scala.concurrent.duration.*

@main def demo(): Unit =
  supervised {
    Source
      .unfold(0)(i => Some(i + 1, i + 1))
      .throttle(1, 1.second)
      .mapPar(4) { i =>
        Thread.sleep(5000)
        val j = i * 3
        j + 1
      }
      .filter(i => i % 2 == 0)
      .zip(Source.unfold(0)(i => Some(i + 1, i + 1)))
      .foreach(println)
  }

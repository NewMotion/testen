package com.thenewmotion.testen

import io.Source._

trait FileAccess {
  def content(name: String) =
    fromInputStream(getClass.getResourceAsStream(name)).getLines().mkString("\n")
}

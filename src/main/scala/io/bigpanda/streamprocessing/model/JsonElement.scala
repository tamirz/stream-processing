package io.bigpanda.streamprocessing.model

sealed trait JsonElement

final case class ValidJsonElement(event_type: String, data: String, timestamp: Long) extends JsonElement

final case class InvalidJsonElement(string: String) extends JsonElement


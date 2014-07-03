package com.codahale.jerkson

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.`type`.{TypeFactory, ArrayType}
import java.util.concurrent.ConcurrentHashMap

import scala.collection.JavaConversions

private[jerkson] object Types {
  private val cachedTypes = JavaConversions.mapAsScalaConcurrentMap(new ConcurrentHashMap[Manifest[_], JavaType]())

  def build(factory: TypeFactory, manifest: Manifest[_]): JavaType =
    cachedTypes.getOrElseUpdate(manifest, constructType(factory, manifest))

  private def constructType(factory: TypeFactory, manifest: Manifest[_]): JavaType = {
    if (manifest.erasure.isArray) {
      ArrayType.construct(factory.constructType(manifest.erasure.getComponentType), null, null)
    } else {
      factory.constructParametricType(
        manifest.erasure,
        manifest.typeArguments.map {m => build(factory, m)}.toArray: _*)
    }
  }
}

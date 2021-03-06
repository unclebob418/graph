package zio.stm.graph

import Key.VertexKey
import Type.VertexType
import zio.stm.graph.Key.VertexKey
import zio.stm.graph.Type.VertexType

case class Vertex[K, V](
  key: VertexKey[K, V],
  vertex: V,
  // maps are both Map[EType, Set[(Edge, VertexKey)]]
  inEs: Map[Any, Set[(Any, Any)]],
  outEs: Map[Any, Set[(Any, Any)]]
)(implicit vType: VertexType[K, V]) {

  def addInE[EK, E, IK, IV](e: E, inVK: VertexKey[IK, IV], eType: Any /*EdgeType[IK,IV, EK, E, K, V]*/ ): Vertex[K, V] =
    //todo validate
    copy(inEs = inEs.get(eType) match {
      case Some(set: Set[(Any, Any)]) => inEs + (eType -> (set + ((e, inVK))))
      case None                       => inEs + (eType -> Set((e, inVK)))
    })

  def addOutE[EK, E, OK, OV](
    e: E,
    outVK: VertexKey[OK, OV],
    eType: Any /*EdgeType[K,V, EK, E, OK, OV]*/
  ): Vertex[K, V] =
    //todo validate
    copy(outEs = outEs.get(eType) match {
      case Some(set: Set[(Any, Any)]) => outEs + (eType -> (set + ((e, outVK))))
      case None                       => outEs + (eType -> Set((e, outVK)))
    })

  def outEs[OK, OV, E0](eType: Any): Option[Set[(Any, Any)]] =
    outEs.get(eType)

  def inEs[OK, OV, E0](eType: Any): Option[Set[(Any, Any)]] =
    inEs.get(eType)
}

object Vertex {
  def apply[K, V, GS <: GraphSchema](
    key: VertexKey[K, V],
    value: V
  )(implicit vType: VertexType[K, V]): Vertex[K, V] =
    Vertex[K, V](key, value, Map.empty[Any, Set[(Any, Any)]], Map.empty[Any, Set[(Any, Any)]])
}

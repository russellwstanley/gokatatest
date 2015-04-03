trait Stone{
  def row : Int
  def column : Int

}

case class WhiteStone(row : Int, column : Int) extends Stone
case class BlackStone(row : Int, column : Int) extends Stone


object GoBoard{

  val width = 9
  val height = 9

  def checkBounds(row : Int,column : Int): Unit ={
    require(row < height)
    require(row > 0)
    require(column < width)
    require(column > 0)
  }

  def containsDuplicates(stones : List[Stone]) : Boolean = {
    stones.map(stone => (stone.row->stone.column)).distinct.size != stones.size
  }
}

class GoBoard(stones : List[Stone] = Nil){



  val whiteStones = stones.filter{
    case s : WhiteStone => true
    case _  => false
  }

  val blackStones = stones.filter{
    case s : BlackStone => true
    case _ => false
  }



  require(blackStones.size == whiteStones.size || blackStones.size == whiteStones.size + 1)
  require(!GoBoard.containsDuplicates(stones))


  def placeStone(stone : Stone) = {
    GoBoard.checkBounds(stone.row,stone.column)
    new GoBoard(stones :+ stone)
  }

//  def placeBlack(row: Int, column: Int): GoBoard = {
//    GoBoard.checkBounds(row,column)
//    new GoBoard(stones :+ BlackStone(row,column))
//  }
//
//
//
//  def placeWhite(row : Int, column : Int): GoBoard = {
//    GoBoard.checkBounds(row,column)
//   new GoBoard(stones :+ WhiteStone(row,column))
//  }


}
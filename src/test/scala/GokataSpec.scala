import org.scalatest._
import org.scalatest.prop.PropertyChecks
import org.scalacheck.Gen



class GokataSpec extends FlatSpec with Matchers with PropertyChecks{

  def generatePoints  = for(row <- 1 to GoBoard.width; column <- 1 to GoBoard.height) yield (row,column)

  val points = Table(("row","column"), generatePoints:_*)


  val rows = Gen.choose(1,GoBoard.width)
  val columns = Gen.choose(1,GoBoard.height)


  "The first stone to be placed" should "be black" in {
    forAll(points){
      (row,column) => {
        val goBoard = new GoBoard()
        intercept[Exception]{
          goBoard.placeStone(new WhiteStone(1,2))
        }
        goBoard.placeStone(new BlackStone(1,2))
      }
    }
  }
  "The second stone placed " should "be white" in {
    val goBoard = new GoBoard()
    goBoard.placeStone(new BlackStone(1,2)).placeStone(new WhiteStone(2,3))
  }
  "A Stone" should "not be placed on occupied points" in {
    val goBoard = new GoBoard(List(BlackStone(1,2)))
    intercept[Exception]{
      goBoard.placeStone(new BlackStone(1,2))
    }
  }
  it should "not be able to be placed outside the board area" in {
    val goBoard = new GoBoard()
    intercept[Exception] {
      goBoard.placeStone(new BlackStone(1,10))
    }
    intercept[Exception] {
      goBoard.placeStone(new BlackStone(0,9))
    }
    intercept[Exception]{
      goBoard.placeStone(new BlackStone(10,1))
    }
    intercept[Exception]{
      goBoard.placeStone(new BlackStone(0,9))
    }
  }
  it should
    """not be able to be placed anywhere where it doesn't have any liberties
      |o o o o o o o o o
      |o o o o o o o o w
      |o o o o o o o w x
      |o o o o o o o o w
      |b b b o o o o o o
      |o o o o o o o o o
      |o o o o o o o o o
      |o o o o o o o o o
      |o o o o o o o o o
      |
    """.stripMargin in {
    val whiteStones = List(WhiteStone(2,9),WhiteStone(3,8),WhiteStone(4,9))
    val blackStones = List(BlackStone(5,1),BlackStone(5,2),BlackStone(5,3))
    val stones = whiteStones ::: blackStones
    val occupiedPoints = stones.map(stone => (stone.row -> stone.column))
    val gb = new GoBoard(whiteStones ::: blackStones)



    val unoccupiedPoints = points.filter{
      case rowCol => ! occupiedPoints.contains(rowCol)
    }

    forAll(unoccupiedPoints){
      case (3,9) => {
        intercept[Exception]{
          gb.placeStone(BlackStone(3,9))
        }
      }
      case (row, column) => gb.placeStone(BlackStone(row,column))

    }
  }
  "GoBoard" should "correctly report duplicates" in {
    GoBoard.containsDuplicates(Nil) shouldBe(false)
    GoBoard.containsDuplicates(List(BlackStone(1,1),WhiteStone(1,1))) shouldBe(true)
    GoBoard.containsDuplicates(List(BlackStone(1,2),WhiteStone(1,1))) shouldBe(false)
  }


}
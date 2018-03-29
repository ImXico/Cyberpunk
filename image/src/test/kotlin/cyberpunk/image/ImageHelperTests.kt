package cyberpunk.image

import org.junit.Test

class ImageHelperTests {

  @Test
  fun `centering on X should keep Y intact`() {
    val newPosition = centerX(20f, 200, 20f)
    assert(newPosition.y == 20f)
  }

  @Test
  fun `centering on Y should keep X intact`() {
    val newPosition = centerY(10f, 300, 40f)
    assert(newPosition.x == 40f)
  }
}

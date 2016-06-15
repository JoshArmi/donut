package io.magentys.donut.gherkin.processors

import java.io.{File, FileNotFoundException}
import io.magentys.donut.gherkin.model.StatusConfiguration
import org.scalatest.{FlatSpec, Matchers}

class JSONProcessorTest extends FlatSpec with Matchers {

  val rootDir = List("src", "test", "resources", "samples-1").mkString("", File.separator, File.separator)
  val statusConfiguration = StatusConfiguration(false, false, false, false)


  behavior of "JSONProcessor"

  it should "identify valid files in a directory" in {
    val jsonFiles = JSONProcessor.getValidFiles(new File(rootDir))
    jsonFiles.size shouldBe 9
    jsonFiles.contains(rootDir + "1.json") shouldBe true
    jsonFiles.contains(rootDir + "2.json") shouldBe true
    jsonFiles.contains(rootDir + "3.json") shouldBe true
    jsonFiles.contains(rootDir + "4.json") shouldBe true
    jsonFiles.contains(rootDir + "5.json") shouldBe true
    jsonFiles.contains(rootDir + "6.json") shouldBe true
    jsonFiles.contains(rootDir + "7.json") shouldBe true
    jsonFiles.contains(rootDir + "8.json") shouldBe true
    jsonFiles.contains(rootDir + "9.json") shouldBe true
    jsonFiles.contains(rootDir + "empty_json.json") shouldBe false
    jsonFiles.contains(rootDir + "sample.xml") shouldBe false
  }

  it should "include json files only" in {
    val jsonFiles = JSONProcessor.getValidFiles(new File(rootDir))
    jsonFiles.map(name => name.endsWith(".json")).reduce(_ && _) shouldBe true
  }

  it should "exclude empty files" in {
    val jsonFiles = JSONProcessor.getValidFiles(new File(rootDir))
    jsonFiles.contains("empty_json.json") shouldBe false
  }

  it should "bind all json object to feature object" in {
    pending
//    val features = JSONProcessor.parseJsonFile(rootDir + "1.json").get
//    features.size shouldBe 1
//    features.head.name shouldBe "Google Journey Performance"
//    features.head.elements.size shouldBe 1
//    features.head.elements.head.name shouldBe "Google Journey Performance"
//    features.head.elements.head.steps.size shouldBe 2
//    features.head.tags.size shouldBe 2
  }

  it should "not parse incorrect file" in {
    intercept[FileNotFoundException] {
      val features = JSONProcessor.parseJsonFile(rootDir + "test.json")
      features shouldBe List.empty
    }
  }

  it should "return empty list if no files available" in {
    JSONProcessor.loadFrom(new File("src/test/resources/samples-empty")) shouldBe List.empty
  }

  it should "handle all weirdos" in {
    val weirdos = JSONProcessor.loadFrom(new File("src/test/resources/samples-weirdos"))
    pending
  }


}
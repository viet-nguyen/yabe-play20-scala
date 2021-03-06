import org.joda.time.DateTime
import play.api._
import models._
import anorm._
import java.util.Date

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    if (app.mode == Mode.Dev) {
      InitialData.insert()
    }
  }

}

/**
 * Initial set of data to be imported 
 * in the sample application.
 */
object InitialData {

  def date(str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str)

  def insert() = {

    if (User.count() == 0) {
      Seq(
        User(Id(1), "bob@gmail.com", "secret", "Bob", false),
        User(Id(2), "jef@gmail.com", "secret", "Jef", false)
      ).foreach(User.create)

      Seq(
        Post(Id(1), "About the model layer", """
                    The model has a central position in a Play! application. It is the domain-specific 
                    representation of the information on which the application operates.
                
                    Martin fowler defines it as:
                    
                    Responsible for representing concepts of the business, information about the 
                    business situation, and business rules. State that reflects the business situation 
                    is controlled and used here, even though the technical details of storing it are 
                    delegated to the infrastructure. This layer is the heart of business software.            
            """, new DateTime("2012-01-01").toDate, 1),
        Post(Id(2), "Just a test of YABE", "Well, it's just a test.", new DateTime("2012-01-02").toDate, 1),
        Post(Id(3), "The MVC Application !", """
                    A Play! application follows the MVC architectural pattern as applied to the 
                    architecture of the Web.
                
                    This pattern splits the application into separate layers: the Presentation 
                    layer and the Model layer. The Presentation layer is further split into a 
                    View and a Controller layer.
            """, new DateTime("2012-01-03").toDate, 2)
      ).foreach(Post.create)

      Seq(
        Comment(NotAssigned, "Guest", "You are right !", new Date, 1),
        Comment(NotAssigned, "Mike", "I knew that ...", new Date, 1),
        Comment(NotAssigned, "Tom", "The post is useless ?", new Date, 2)
      ).foreach(Comment.create)
      
      Seq(
        Tag(Id(1), "Play"),
        Tag(Id(2), "Architecture"),
        Tag(Id(3), "Test"),
        Tag(Id(4), "MVC")
      ).foreach(Tag.create)
      
      Seq(
        TagsForPosts(1, 1),
        TagsForPosts(1, 2),
        TagsForPosts(3, 2),
        TagsForPosts(4, 3)
      ).foreach(TagsForPosts.create)

    }

  }

}
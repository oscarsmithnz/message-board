package yeah;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-01T16:49:18")
@StaticMetamodel(Post.class)
public class Post_ { 

    public static volatile SingularAttribute<Post, Long> parentPost;
    public static volatile SingularAttribute<Post, Long> postID;
    public static volatile SingularAttribute<Post, String> time;
    public static volatile SingularAttribute<Post, String> user;
    public static volatile SingularAttribute<Post, String> content;

}
package com.stevesouza.resttemplate.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;

/**
 *  populated with data from public api: https://jsonplaceholder.typicode.com/posts
 *
 *  Here is the json format it returns, but many are returned in array form.  Note just to be sure that I am creating
 *  json and returning it to the front end as List<Post> I am adding an extra field populated with default data.
 *  {
 * userId: 1,
 * id: 1,
 * title: "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
 * body: "quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam nostrum rerum est autem sunt rem eveniet architecto"
 * },
 *
 *  @Column(name="strField3")
 * 	@NotNull
 * 	@Size(min=2, message="Name should have at least 2 characters")
 * 	private String str;
 *
 * 	// note this will error out if value isn't between 0 and 100 however it will
 * 	// only send an 500 error.  Further work must be done to send a more specific error
 * 	@Min(0)
 * 	@Max(100)
 */

@Entity
public class Post {
    @Id
    @GeneratedValue
    private long id;
    private String userId;
    private String title;
    private String body;
    //@Max(10)
    private String name="Joe";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}

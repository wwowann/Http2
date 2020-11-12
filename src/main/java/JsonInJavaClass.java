import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonInJavaClass {
    public String copyright;
    public String date;
    public String explanation;
    public String hdurl;
    public String service_version;
    public String title;
    public String url;


    public JsonInJavaClass(
            @JsonProperty("copyright") String copyright,
            @JsonProperty("date") String date,
            @JsonProperty("explanation") String explanation,
            @JsonProperty("hdurl") String hdurl,
            @JsonProperty("service_version") String service_version,
            @JsonProperty("title") String title,
            @JsonProperty("url") String url
    )
    {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.service_version = service_version;
        this.title = title;
        this.url = url;

    }

    public String getUrl() {
        return url;
    }
}

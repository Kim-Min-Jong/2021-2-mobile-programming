package com.example.project2;

public class Sentence {
    private Integer id;
    private String content;
    private String[] answers;
    private Integer dfficulty;
    private boolean published = false;
    private boolean passed = false;


    Sentence(String id, String content, String dfficulty, String[] answers){
        this.id = Integer.parseInt(id);
        this.content = content;
        this.dfficulty = Integer.parseInt(dfficulty);
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Integer getDfficulty() {
        return this.dfficulty;
    }

    public void setPassed() {
        this.passed = true;
    }
    public void setPublished(){
        this.published=true;
    }
    public boolean isPassed(){
        return this.passed;
    }
    public void setAnsewrs(String [] anss){
        answers=anss;
    }
    public String[] getAnswers(){
            return answers;
    }
}

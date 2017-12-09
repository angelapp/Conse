package conse.nrc.org.co.consejo.Utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.Activities.MainActivity;

/**
 * Created by apple on 11/18/17.
 */

public class Models {

    public static class RegisterUserProfileModel {

        public String first_name;
        public String last_name;
        public String email;
        public String password;

        public String birthdate;
        public String document_number;
        public String contact_phone;
        public String address;
        public int role;
        public int gender;
        public int ethnic_group;
        public int condition;
        public int document_type;
        public int origin_city;
        public int actual_city;

        public Boolean isNRCBeneficiary;

        public float latitude;
        public float longitude;
    }

    public static class RegisterUserResponse{

        public String token;
        public int id;
        public UserSerializer user;
        public ProfileSerializer profile;
    }


    public static class UserSerializer{

        public int id;
        public String first_name;
        public String last_name;
        public String username;
        public String email;
    }

    public static class ProfileSerializer{

        public int id;
        public String document_number;
        public String birthdate;
        public boolean isNRCBeneficiary;
        public String contact_phone;
        public Role role;
        public Gender gender;
        public EthnicGroup ethnic_group;
        public Condition condition;
        public DocumentType document_type;
        public City origin_city;
    }

    public static class ApplicationConfiguration{

        public String video_tutorial_id;
        public String emergency_message;
        public String about_noruegan_council;
        public int min_pin_length;

        public List<DocumentType> document_type_list;
        public List<Gender> gender_list;
        public List<City> city_list;
        public List<Condition> condition_list;
        public List<EthnicGroup> ethnic_group_list;
        public List<Role> role_list;
        public List<BodyParts> body_parts_list;
        public List<AvatarPiece> avatar_pieces_list;
        public List<ContactFormType> contact_form_type_list;
        public List<Course> course_list;


        public List<AvatarPiece> getAvatarPiecesByGenderAndPart(int gender, int body_part){
            List<AvatarPiece> list = new ArrayList<>();

            for (AvatarPiece piece:avatar_pieces_list){
                if (piece.gender == gender && piece.body_part == body_part) {
                    list.add(piece);
                }
            }
            return list;
        }

        public AvatarPiece getAvatarPieceById(int id){
            for (AvatarPiece piece : avatar_pieces_list){
                if (piece.id == id){
                    return piece;
                }
            }
            return null;
        }


        public Course getCourseById(int course_id){
            for(Course course : course_list){
                if (course.id == course_id){
                    return course;
                }
            }
            return null;
        }

        public Topic getTopicById(int topic_id){
            for(Course course : course_list) {
                for (Topic topic : course.course_topics){
                    if (topic.id == topic_id) {
                        return topic;
                    }
                }
            }
            return null;
        }

        public TopicActivity getTopicActivityById(int activity_id){
            for(Course course : course_list) {
                for (Topic topic : course.course_topics) {
                    for (TopicActivity activity : topic.topic_activity_list) {
                        if (activity.id == activity_id) {
                            return activity;
                        }
                    }
                }
            }
            return null;
        }
    }

    public static class DocumentType{

        public int id;
        public String name;
        public String abreviature;
        public String description;
        public String icon;

    }

    public static class Gender{

        public int id;
        public String name;
        public String abreviature;
        public String icon;

    }

    public static class City{

        public int id;
        public String name;
        public String code;
        public String abreviature;
        public String state;
        public String description;
        public String icon;

    }

    public static class Condition{

        public int id;
        public String name;
        public String abreviature;
        public String description;
        public String icon;

    }

    public static class EthnicGroup{

        public int id;
        public String name;
        public String abreviature;
        public String description;
        public String icon;

    }

    public static class Role{

        public int id;
        public String name;
        public String abreviature;
        public String description;
        public String icon;

    }

    public static class BodyParts{

        public int id;
        public String name;
        public String abreviature;
        public String description;
        public String icon;

    }

    public static class AvatarPiece{

        public int id;
        public int body_part;
        public int gender;
        public String name;
        public String description;
        public String icon;

    }

    public static class UserAvatar{
        public int user;
        public int avatar_piece;

        public UserAvatar(int user, int avatar_piece){
            this.user = user;
            this.avatar_piece = avatar_piece;
        }
    }

    public static class ContactFormType{

        public int id;
        public String name;
        public String abreviature;
        public String description;
        public String icon;

    }


    public static class ContactForm {
        public int user;
        public int message_type;
        public String detail;
    }

    public static class Course {
        public int id;
        public String name;
        public String abreviature;
        public String description;
        public String icon;
        public List<Topic> course_topics;
    }

    public static class Topic{
        public int id;
        public int course;
        public String name;
        public String abreviature;
        public String description;
        public String icon;

        public List<TopicActivity> topic_activity_list;

        public int topicTotalPoints(){
            int points = 0;
            for (TopicActivity activity : topic_activity_list){
                points += activity.ponderation_progress;
            }
            Log.d("Models", "Total points in " + this.name + ": " + points);
            return points;
        }

        public int getUserProgressByTopic(){
            int progress_percentage = 0;
            for (TopicActivity activity : MainActivity.dataBase.getTopicActivitiesCompleted()){
                if (activity.topic == this.id){
                    Log.d("Models", "the user has completed the activity: " + activity.description + " points: " + activity.ponderation_progress);
                    progress_percentage += activity.ponderation_progress;
                }
            }
            Log.d("Models", "Total points winned in " + this.name + ": " + progress_percentage);
            if(topicTotalPoints() == 0 ){
                return 0;
            }

            return (int)((float)progress_percentage/(float)topicTotalPoints()*100);
        }
    }

    public static class TopicActivity{
        public int id;
        public int topic;
        public String name;
        public String abreviature;
        public String description;
        public String icon;
        public int ponderation_progress;

    }

    public static class UserActivityProgress{
        public int topic_activity;
        public int user;
        public String date_completed;

        public UserActivityProgress(int topic_activity, int user){
            this.topic_activity = topic_activity;
            this.user = user;
            this.date_completed = UtilsFunctions.getDate(System.currentTimeMillis());
        }

        public UserActivityProgress(int topic_activity, int user, String date_completed){
            this.topic_activity = topic_activity;
            this.user = user;
            this.date_completed = date_completed;
        }
    }
}

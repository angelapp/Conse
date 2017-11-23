package conse.nrc.org.co.consejo.Utils;

import java.util.ArrayList;
import java.util.List;

import conse.nrc.org.co.consejo.Activities.Avatar;

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

        public List<AvatarPiece> getAvatarPiecesByGenderAndPart(int gender, int body_part){
            List<AvatarPiece> list = new ArrayList<>();

            for (AvatarPiece piece:avatar_pieces_list){
                if (piece.gender == gender && piece.body_part == body_part) {
                    list.add(piece);
                }
            }
            return list;
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
}

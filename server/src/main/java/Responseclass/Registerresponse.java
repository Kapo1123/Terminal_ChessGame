package Responseclass;

import Requestclasses.Authtoken;

public record Registerresponse(String username, Authtoken auth) {

}

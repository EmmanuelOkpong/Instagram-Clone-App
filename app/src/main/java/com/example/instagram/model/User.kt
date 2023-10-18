package com.example.instagram.model

class User{
    private var bio:String=""
    private var fullName:String=""
    private var uid:String=""
    private var userName:String=""
    private var image:String=""

constructor()

    constructor(userName:String,fullName:String,bio:String,image:String, uid:String)
    {
        this.fullName =fullName
        this.userName= userName
        this.uid = uid
        this.bio = bio
        this.image = image
  }

    fun getFullName():String{
     return fullName

 }
    fun setFullName(fullName: String){
     this.fullName=fullName
}

    fun getUserName():String{
        return userName
    }
    fun setUsername(userName: String){
        this.userName=userName
    }


    fun getUid():String{
        return uid
    }
    fun setUid(uid: String){
        this.uid=uid
    }


    fun getBio():String{
        return bio
    }
    fun setBio(bio: String){
        this.bio=bio
    }

    fun getImage():String{
        return image
    }
    fun setImage(image: String){
        this.image=image
    }


}
import { Component, OnInit,ElementRef,ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface UserData {
        id: number;
        name: string;
        customPronuncitionInd : boolean;
        username : string;
        userrole : string;
    }

@Component({
  selector: 'app-voicedisplay',
  templateUrl: './voicedisplay.component.html',
  styleUrls: ['./voicedisplay.component.css']
})
export class VoicedisplayComponent implements OnInit {
  name = 'Angular';
  audioSource = '';
  fName = '';
  lName = '';
  selectedObject='en-US-ChristopherNeural';
  //customPronuncitionOptIn=false;
  customPronuncitionOptIn: boolean | undefined;
  showCustomRecordingOption: boolean | undefined;
  //names = [{country:'en-IN',neural:'en-IN-PrabhatNeural'}, {country:'en-US',neural:'en-US-ChristopherNeural'}];
  names :any[] | undefined;

  userInfo : any;


  @ViewChild('audioTag') audioTag:ElementRef | undefined;

  constructor(private http:HttpClient) {

  }

  ngOnInit(): void {
    this.names=[{'country':'en-IN','neural':'en-IN-PrabhatNeural'}, {'country':'en-US','neural':'en-US-ChristopherNeural'},{'country':'en-GB','neural':'en-GB-RyanNeural'}];
    let user = sessionStorage.getItem('username');
    /*if(user=='jamie') {
      this.fName = 'Jamie';
      this.lName = 'Lannister';
      this.customPronuncitionOptIn = true;
    } else if(user=='cersie'){
      this.fName = 'Cersie';
      this.lName = 'Lannister';
    }*/
    console.log("ngOnInit");
    this.loadUserData();

  }

  public customRecordingOptInChanged(value:boolean){
    this.showCustomRecordingOption = value;
}

  loadUserData() {
    var baseURL = window.location.protocol + '//' + window.location.host;
    console.log("getUserData: " + baseURL);
    let user = sessionStorage.getItem('username');
    console.log("User:" + user);

    if(user != null) {
      this.http.post<UserData>(baseURL+"/getUserData", { userName: user }).subscribe(data => {
           this.userInfo = data;

              this.fName = data.name;
              this.lName = data.name;
              console.log('userInfo.name ' + this.userInfo.name);
          })

    }
  }

  loadAudioClick(){
    var baseURL = window.location.protocol + '//' + window.location.host;
    if(this.showCustomRecordingOption || this.fName == 'Jamie') {

      let response = this.http.get(baseURL+"/getCustomRecording/"+this.fName+"/"+this.lName, {responseType: 'blob'});
      response.subscribe((data)=>{
            let blob=new Blob([data], {type : 'audio/ogg'});
            let blobUrl = URL.createObjectURL(blob);
            this.audioSource = blobUrl;
         });
    } else{
      let response = this.http.get(baseURL+"/message/"+this.fName+"/"+this.lName+"/"+this.selectedObject, {responseType: 'blob'});
      response.subscribe((data)=>{
            let blob=new Blob([data], {type : 'audio/ogg'});
            let blobUrl = URL.createObjectURL(blob);
            this.audioSource = blobUrl;
            console.log(this.audioSource);
         });
    }
    }
    convertDataURIToBinary(dataURI: String) {
      let BASE64_MARKER = ';base64,';
      let base64Index = dataURI.indexOf(BASE64_MARKER) + BASE64_MARKER.length;
      let base64 = dataURI.substring(base64Index);
      let raw = window.atob(base64);
      let rawLength = raw.length;
      let array = new Uint8Array(new ArrayBuffer(rawLength));

      for(let i = 0; i < rawLength; i++) {
        array[i] = raw.charCodeAt(i);
      }
      return array;
    }

  }



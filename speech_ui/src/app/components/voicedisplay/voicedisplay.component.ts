import { Component, OnInit,ElementRef,ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';

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
  //customPronuncitionOptIn=false;
  customPronuncitionOptIn: boolean | undefined;
  showCustomRecordingOption: boolean | undefined;

  @ViewChild('audioTag') audioTag:ElementRef | undefined;

  constructor(private http:HttpClient) { }

  ngOnInit(): void {

      let response = this.http.get("http://localhost:8080/message/"+this.fName, {responseType: 'blob'});
      response.subscribe((data)=>{
            let blob=new Blob([data], {type : 'audio/ogg'});
            let blobUrl = URL.createObjectURL(blob);
            this.audioSource = blobUrl;
      });

  }

  public customRecordingOptInChanged(value:boolean){
    this.showCustomRecordingOption = value;
}

  

  loadAudioClick(){
    if(this.showCustomRecordingOption) {
      let response = this.http.get("http://localhost:8080/getCustomRecording/"+this.fName+"/"+this.lName, {responseType: 'blob'});
      response.subscribe((data)=>{
            let blob=new Blob([data], {type : 'audio/ogg'});
            let blobUrl = URL.createObjectURL(blob);
            this.audioSource = blobUrl;
         });
    } else{
      let response = this.http.get("http://localhost:8080/message/"+this.fName+"/"+this.lName, {responseType: 'blob'});
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



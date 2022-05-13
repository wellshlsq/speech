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

  @ViewChild('audioTag') audioTag:ElementRef | undefined;

  constructor(private http:HttpClient) { }

  ngOnInit(): void {

      let response = this.http.get("http://localhost:8080/message", {responseType: 'blob'});
      response.subscribe((data)=>{
                                      //console.log(data);
  //                                     let binary= this.convertDataURIToBinary(JSON.stringify(data));
                                          let blob=new Blob([data], {type : 'audio/ogg'});
                                          let blobUrl = URL.createObjectURL(blob);
                                          this.audioSource = blobUrl;
  //                                         this.audioTag.nativeElement.setAttribute('src',this.audioSource);
                                          console.log(this.audioSource);
                                       });

      }

  loadAudioClick(){

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



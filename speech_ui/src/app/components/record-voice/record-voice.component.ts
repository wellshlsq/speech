import { Component, OnInit,OnDestroy,ChangeDetectionStrategy, ChangeDetectorRef, ViewChild  } from '@angular/core';

import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { AudioRecordingService } from './audio-recording.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-record-voice',
  templateUrl: './record-voice.component.html',
  styleUrls: ['./record-voice.component.css'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class RecordVoiceComponent implements OnDestroy {
  isPlaying = false;
  displayControls = true;
  isAudioRecording = false;
  audioRecordedTime;
  audioBlobUrl;
  audioBlob;
  audioName;
  audioStream;
  audioConf = { audio: true}
constructor(
    private ref: ChangeDetectorRef,
    private audioRecordingService: AudioRecordingService,
    private http:HttpClient,
    private sanitizer: DomSanitizer
  ) {


    this.audioRecordingService.recordingFailed().subscribe(() => {
      this.isAudioRecording = false;
      this.ref.detectChanges();
 });

    this.audioRecordingService.getRecordedTime().subscribe((time) => {
      this.audioRecordedTime = time;
      this.ref.detectChanges();
    });

    this.audioRecordingService.getRecordedBlob().subscribe((data) => {
      this.audioBlob = data.blob;
      this.audioName = data.title;
      this.audioBlobUrl = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.blob));
      this.ref.detectChanges();
    });
  }
  startAudioRecording() {
      if (!this.isAudioRecording) {
        this.isAudioRecording = true;
        this.audioRecordingService.startRecording();
      }
    }

    abortAudioRecording() {
      if (this.isAudioRecording) {
        this.isAudioRecording = false;
        this.audioRecordingService.abortRecording();
      }
    }

    stopAudioRecording() {
      if (this.isAudioRecording) {
        this.audioRecordingService.stopRecording();
        this.isAudioRecording = false;
      }
    }

    clearAudioRecordedData() {
      this.audioBlobUrl = null;
    }

    downloadAudioRecordedData() {
      this._downloadFile(this.audioBlob, 'audio/mp3', this.audioName);
    }

  uploadAudioRecordedData() {
      //this._downloadFile(this.audioBlob, 'audio/mp3', this.audioName);
     //const audioBlob = new Blob([this.audioBlob], { type: 'audio/mp3' });
     //const string = await this.audioBlob.text();
     const reader = new FileReader();
     // This fires after the blob has been read/loaded.
reader.addEventListener('loadend', (e) => {
  const text = e.target?.result;
  console.log(text);
  this.http.post<any>('http://localhost:8080/uploadRecording',{'name':'Bob','audioBlob':text})
       .subscribe(data => {
        console.log("testtttt  "+data);})
});

// Start reading the blob as text.
      reader.readAsText(this.audioBlob);
      // this.http.post<any>('http://localhost:8080/uploadRecording',{'name':'Bob','audioBlob':this.audioBlob})
       //.subscribe(data => {
       // console.log("testtttt  "+data);
      //})
           /* response.subscribe((data)=>{
                                            //console.log(data);
        //                                     let binary= this.convertDataURIToBinary(JSON.stringify(data));
                                                let blob=new Blob([data], {type : 'audio/ogg'});
                                                let blobUrl = URL.createObjectURL(blob);
                                                this.audioSource = blobUrl;
        //                                         this.audioTag.nativeElement.setAttribute('src',this.audioSource);
                                                console.log(this.audioSource);
                                             });*/
                                            }
    ngOnDestroy(): void {
      this.abortAudioRecording();
    }

    _downloadFile(data: any, type: string, filename: string): any {
        const blob = new Blob([data], { type: type });
        const url = window.URL.createObjectURL(blob);
        //this.video.srcObject = stream;
        //const url = data;
        const anchor = document.createElement('a');
        anchor.download = filename;
        anchor.href = url;
        document.body.appendChild(anchor);
        anchor.click();
        document.body.removeChild(anchor);
      }

}

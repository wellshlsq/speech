import { Component, OnInit,OnDestroy,ChangeDetectionStrategy, ChangeDetectorRef, ViewChild,Input   } from '@angular/core';

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
  audioConf = { audio: true};

  @Input() fName;
  @Input() lName;

constructor(
    private ref: ChangeDetectorRef,
    private audioRecordingService: AudioRecordingService,
    private http:HttpClient,
    private sanitizer: DomSanitizer,
    
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

  uploadAudioRecordedData() {
    var baseURL = window.location.protocol + '//' + window.location.host;
    this.http.post<any>(baseURL+'/uploadRecording/'+this.fName+"/"+this.lName,this.audioBlob,{ responseType: 'blob' as 'json'})
       .subscribe(data => {
      })
  }
    ngOnDestroy(): void {
      this.abortAudioRecording();
    }

}

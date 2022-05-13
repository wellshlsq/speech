import { Component, OnInit } from '@angular/core';
import * as RecordRTC from 'recordrtc';
declare var $: any;
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-record-voice',
  templateUrl: './record-voice.component.html',
  styleUrls: ['./record-voice.component.css']
})
export class RecordVoiceComponent implements OnInit {

  title = 'micRecorder';
  //Lets declare Record OBJ
  record: RecordRTC.StereoAudioRecorder | undefined;
  //Will use this flag for toggeling recording
  recording = false;
  //URL of Blob
  url: string | undefined;
  error: string | undefined;
  constructor(private domSanitizer: DomSanitizer) {}
  sanitize(url: string) {
  return this.domSanitizer.bypassSecurityTrustUrl(url);
  }
  /**
  * Start recording.
  */
  initiateRecording() {
  this.recording = true;
  let mediaConstraints = {
  video: false,
  audio: true
  };
  navigator.mediaDevices.getUserMedia(mediaConstraints).then(this.successCallback.bind(this), this.errorCallback.bind(this));
  }
  /**
  * Will be called automatically.
  */
  successCallback(stream: MediaStream) {
  var options = {
  mimeType: "audio/wav",
  numberOfAudioChannels: 1,
  sampleRate: 16000,
  };
  //Start Actuall Recording
  var StereoAudioRecorder = RecordRTC.StereoAudioRecorder;
  this.record = new StereoAudioRecorder(stream, options);
  this.record.record();
  }
  /**
  * Stop recording.
  */
  stopRecording() {
  this.recording = false;
  this.record.stop(this.processRecording.bind(this));
  }
  /**
  * processRecording Do what ever you want with blob
  * @param  {any} blob Blog
  */
  processRecording(blob: Blob | MediaSource) {
  this.url = URL.createObjectURL(blob);
  console.log("blob", blob);
  console.log("url", this.url);
  }
  /**
  * Process Error.
  */
  errorCallback(error: any) {
  this.error = 'Can not play audio in your browser';
  }
  ngOnInit() {}

}

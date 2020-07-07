import { Component, OnInit, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { DataTableDirective} from 'angular-datatables';
import { Item } from './item.model'
import { InventoryService} from './inventory.service'
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  webSocketEndPoint: string = 'http://localhost:8080/ws';
  topic: string = "/topic/inventory";
  stompClient: any;

  @ViewChild(DataTableDirective) datatableElement: DataTableDirective;

  items : Item[] = [];
  tempItems : Item[] = [];
  dtTrigger: Subject<any> = new Subject();
  dtOptions: DataTables.Settings = {};

  title = 'Inventory Viewing System';

  constructor(private inventoryService: InventoryService) {
  };

  ngOnInit(): void {
    this.dtOptions = {
      order: [[0, "asc"]],
      autoWidth: true,
      columns: [
        { title: 'SKU', data: 'sku' },
        { title: 'Name', data: 'name' },
        { title: 'Count', data: 'count' },
      ]
    };

    this.connect();
    this.items = [];

    this.getInventoryRecords().subscribe(result => { 
      
      this.tempItems = result;
      
      for(let i=0; i<this.tempItems.length; i++){
        console.log(this.tempItems[i].name);
        this.items.push(this.tempItems[i]);
      }
    });

    this.dtTrigger.next();
    this.rerender();
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  rerender(): void {
      this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
        dtInstance.destroy();
        this.dtTrigger.next();
      });
  }
  
  getInventoryRecords() {
    return this.inventoryService.fetchAllInventoryRecords();
  }

  connect(){
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        const _this = this;
        _this.stompClient.connect({}, function (frame) {
            _this.stompClient.subscribe(_this.topic, function (sdkEvent) {
                _this.onMessageReceived(sdkEvent);
            });
        }, this.errorCallBack);
  }

  errorCallBack(error) {
    setTimeout(() => {
        this.connect();
    }, 5000);
  }

  onMessageReceived(message: any) {
    this.handleMessage(JSON.parse(message.body));
  }

  handleMessage(message: any) {
    console.log("New Event Received=> SKU: " + message.sku + " Name: " + message.name + " Count: " + message.count);

    this.getInventoryRecords().subscribe(result => { 
      
      this.tempItems = result;
      this.items = [];
      
      for(let i=0; i<this.tempItems.length; i++){
        console.log(this.tempItems[i].name);
        this.items.push(this.tempItems[i]);
      }
    });

    this.rerender();
  }
}

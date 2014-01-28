/*
json_file_store.js - a data store adapter that uses a directory of json files as a read-only source
 
Copyright (c) 2013, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this list 
  of conditions and the following disclaimer.
* Redistributions in binary form must reproduce the above copyright notice, this 
  list of conditions and the following disclaimer in the documentation and/or other 
  materials provided with the distribution.
* Neither the name of Intel Corporation nor the names of its contributors may be 
  used to endorse or promote products derived from this software without specific 
  prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.


License: BSD3
Version: 0.1
Requires: prototype.js 1.7.1 or later
 */

var JsonFileStore = {};

(function(JsonFileStore, undefined) {
    
    JsonFileStore.VERSION = "0.1";
    JsonFileStore.BASEURL = "json_file_store/";
    
    JsonFileStore.collectionUrl = function(resourceObject) {
        // we can only return a collection url if this resource is a collection
        if( !!resourceObject['collection'] ) {
            if( !!resourceObject['href'] ) {
                // a specific href is given so we use that
                return resourceObject['href'];
            }
            // convention is for the URL to be {baseurl}/{collection}.json  for example  json_file_store/hosts.json
            return JsonFileStore.BASEURL+resourceObject['collection']+".json";
        }
        // it's not a collection
        return null;
    };
    
    JsonFileStore.search = function(criteria) {
        // because we're using read-only file resources, in order to search for a record we need to 
        // read in the entire collection and then search in memory 
        // TODO:  read the json file at collectionUrl, then search it with criteria and return results
    };
    
})(JsonFileStore);

     function setIframeHeight(iframe) {
     	     iframe.height = 0;
          if (iframe) {    
           var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
             
           if (iframeWin.document.body) {
           iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
       }
      }
     };

<%@ page contentType="application/download;charset=UTF-8" import=
"java.io.*,
org.apache.batik.transcoder.image.JPEGTranscoder,
org.apache.batik.transcoder.image.PNGTranscoder,
org.apache.batik.transcoder.TranscoderInput,
org.apache.batik.transcoder.TranscoderOutput,
java.net.*,
java.awt.Color,
org.apache.fop.svg.PDFTranscoder,
java.lang.String,
javax.servlet.http.*,
websquare.util.*,
websquare.logging.util.*,
websquare.system.*,
websquare.*,
org.w3c.dom.*"%>
<%!
public void doScan( NodeList nodeList, StringBuffer sb ) {
    for (int i = 0; i < nodeList.getLength(); i++) {
        Node child = nodeList.item(i);

        if (child.getNodeType() == Node.ELEMENT_NODE ) {
            String nodeName = child.getNodeName();
            String attrStr = getAttr( child );
            sb.append( "<" + nodeName + attrStr + ">" );

            NodeList subChild = child.getChildNodes();
            if( subChild.getLength() > 0 ) {
                doScan( subChild, sb );
            }

            sb.append( "</" + nodeName + ">" );
        } else if (child.getNodeType() == Node.TEXT_NODE ) {
            String textContent = child.getTextContent();
            if( textContent.length() > 0 ) {
                sb.append( textContent );
            }
        }
    }
}

public String getAttr( Node node ) {
    String attrStr = "";
    NamedNodeMap attrs = null;
    attrs = node.getAttributes();

    try {
        if( attrs != null ) {
            for (int i = 0; i < attrs.getLength(); i++) {
                Node item = attrs.item(i);
                String nodeName = item.getNodeName();
                String nodeValue = XMLUtil.XMLEncoder( item.getNodeValue() );

                if( nodeName.equals( "style" ) ) {

                    String style = "";
                    String styleKey = "";
                    String styleValue = "";
                    if( nodeValue.length() > 0 ) {
                        String[] styleArr = nodeValue.split(";");
                        for( int j=0;j<styleArr.length;j++ ) {
                            String[] attArr = styleArr[j].split( ":" );
                            styleKey = attArr[0];
                            styleValue = attArr[1];
                            if( styleKey.indexOf( "opacity" ) > -1 && styleValue.indexOf( "e" ) > -1 ) {
                                Float fl = Float.parseFloat( attArr[1].trim() );  //지수 표현 1e-06
                                styleValue = String.format( "%f", fl );
                            }
                            style = style + styleKey + ":" + styleValue + ";";
                        }
                    }

                    nodeValue = style;

                } else if( nodeValue.equals( "undefined" ) ) {
                    attrStr = "";
                } else {
                    attrStr = attrStr + " " + nodeName + "=" + "\"" + nodeValue + "\"";
                }
            }
        }
    } catch(Exception e) {
    }

    return attrStr;
}
%><%
    //  주의사항 batik svg 라이브러리로 jpg,png export시 jdk 1.7을 사용할 경우
    //  chart svg의 text anti-aliasing이 깨져서 export되니 1.6 이나 1.5를 권장합니다.
    //  batik 버그질라에 올라와 있으나 버그가 fixed되지 않았습니다.
    //  https://issues.apache.org/jira/browse/BATIK-1027

    String WEBSQUARE_HOME = System.getProperty("WEBSQUARE_HOME");
    String download_encoding = WebSquareConfig.getInstance().getDownloadEncoding();
    long currentMillis = System.currentTimeMillis();
    java.util.Random rand = new java.util.Random(currentMillis);
    String path = WEBSQUARE_HOME+"/temp/";

    String svgStr = HttpUtil.getParameter( request, "xmlValue" );
    svgStr = URLDecoder.decode(svgStr, download_encoding);
    String[] str = svgStr.split("ExportType=");
    svgStr = str[0];

    String type_str = str[1];
    String[] typeArr = type_str.split("ExportFileName=");
    String type = typeArr[0];

    String fileName_str = typeArr[1];
    String fileName = fileName_str+"."+type;
    String tempFileName =  currentMillis + "_" + rand.nextInt(100);

    StringBuffer sb = new StringBuffer();
    Document dom = XMLUtil.getDocument( svgStr );
    Element root = dom.getDocumentElement();
    String rootAttr = getAttr( root );
    NodeList nodeList = root.getChildNodes();

    sb.append( "<svg " + rootAttr + ">\n" );
    doScan( nodeList, sb );
    sb.append( "</svg>" );

    StringReader svgString = new StringReader( sb.toString() );
    sb.delete( 0, sb.length() );
    String ext = "application/download";

    if(type.equals("jpg")){
        ext = "image/jpeg";
    } else if(type.equals("png")){
        ext = "image/png";
    } else if(type.equals("pdf")){
        ext = "application/pdf";
    }
	response.reset();
    response.setContentType(ext);
	if( fileName.indexOf("%00") > -1 || fileName.toLowerCase().indexOf("%zz") > -1 || fileName.indexOf(";") > -1) {
		throw new Exception("[" + fileName + "]This file has been rejected by the server. please check file name[%00,%zz, ;]");
	}
    if( fileName.indexOf("." + File.separator) > -1 || fileName.indexOf("..") > -1 || fileName.indexOf(File.separator) > -1 ) {
        throw new Exception( "This file has been rejected by the server. please check file name["+fileName+"]");
    }

    String headerFileName = fileName.replaceAll("\r\n", "");
    String userAgent = request.getHeader("User-Agent");
    if( ( userAgent.indexOf("MSIE") > -1 ) || userAgent.indexOf( "Edge" ) > -1 || ( userAgent.indexOf( "Windows" ) > -1 && userAgent.indexOf( "Trident/7.0" ) > -1 ) ) {
        headerFileName =  URLEncoder.encode(headerFileName, download_encoding);
        headerFileName = headerFileName.replaceAll("[+]","%20");
        response.setHeader("Content-Disposition", "attachment;filename="+headerFileName+";");
    } else {
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(headerFileName.getBytes(download_encoding), WebSquareConfig.getInstance().getPostMethodEncoding() )+";");
    }

    try{
        if(type.equals("jpg")|| type.equals("jpeg")){
            JPEGTranscoder jpg_t = new JPEGTranscoder();
            jpg_t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,new Float(.95));

            TranscoderInput input = new TranscoderInput(svgString);

            File temp = new File(path);
            if(!temp.exists()){
                temp.mkdirs();
            }

            OutputStream ostream = new FileOutputStream(path+tempFileName+".jpg");
            TranscoderOutput output = new TranscoderOutput(ostream);

            jpg_t.transcode(input, output);

            ostream.flush();
            ostream.close();

            try{
                ServletOutputStream sos = response.getOutputStream();
                String fiilePathAndName = path+tempFileName+".jpg";
                File file = new File(fiilePathAndName);
                byte[] data = new byte[1024];
                FileInputStream fis = new FileInputStream(file);
                int count = 0;

                while((count = fis.read(data)) != -1){
                    sos.write(data,0,count);
                    sos.flush();
                }
                if(fis != null) fis.close();
                if(sos != null) sos.close();

            }catch(Exception e){
                LogUtil.exception("download error ", e);
            }
            out.clear();
            out = pageContext.pushBody();
        } else if (type.equals("png")){

            PNGTranscoder png_t = new PNGTranscoder();
            // Set the transcoding hints.
            png_t.addTranscodingHint(PNGTranscoder.KEY_BACKGROUND_COLOR, Color.WHITE);
            TranscoderInput input2 = new TranscoderInput(svgString);
            // Create the transcoder output.
            File temp = new File(path);
            if(!temp.exists()){
                temp.mkdirs();
            }

            OutputStream ostream2 = new FileOutputStream(path+tempFileName+".png");
            TranscoderOutput output2 = new TranscoderOutput(ostream2);
            // Save the image.
            png_t.transcode(input2, output2);
            ostream2.flush();
            ostream2.close();

            try{

                ServletOutputStream sos = response.getOutputStream();
                String fiilePathAndName = path+tempFileName+".png";
                File file = new File(fiilePathAndName);
                byte[] data = new byte[1024];
                FileInputStream fis = new FileInputStream(file);
                int count = 0;

                while((count = fis.read(data)) != -1){
                    sos.write(data,0,count);
                    sos.flush();
                }
                if(fis != null) fis.close();
                if(sos != null) sos.close();

            }catch(Exception e){
                LogUtil.exception("download error ", e);
            }
            out.clear();
            out = pageContext.pushBody();
        } else if (type.equals("pdf")){

            PDFTranscoder pdf_t = new PDFTranscoder();
            // Set the transcoding hints.
            TranscoderInput input3 = new TranscoderInput(svgString);
            // Create the transcoder output.
            File temp = new File(path);
            if(!temp.exists()){
                temp.mkdirs();
            }

            OutputStream ostream3 = new FileOutputStream(path+tempFileName+".pdf");
            TranscoderOutput output3 = new TranscoderOutput(ostream3);
            // Save the image.
            pdf_t.transcode(input3, output3);
            ostream3.flush();
            ostream3.close();

            try{
                ServletOutputStream sos = response.getOutputStream();
                String fiilePathAndName = path+tempFileName+".pdf";
                File file = new File(fiilePathAndName);
                byte[] data = new byte[1024];
                FileInputStream fis = new FileInputStream(file);
                int count = 0;

                while((count = fis.read(data)) != -1){
                    sos.write(data,0,count);
                    sos.flush();
                }
                if(fis != null) fis.close();
                if(sos != null) sos.close();

            }catch(Exception e){
                LogUtil.exception("download error ", e);
            }
            out.clear();
            out = pageContext.pushBody();
        }
    }catch(Exception e){
    	LogUtil.exception("[I18NReload.jsp] Exception.", e);
    } finally {

        try {
            String deletePath = path + tempFileName + "." + type;

            File f = new File(deletePath);
            if( f.exists() ) {
                boolean ret = FileUtil.delete(f);
                if(! ret ) {
                    LogUtil.fine("[exportFusionChart.jsp]Couldn't delete file : " + deletePath);
                }
            }
        } catch (Exception ex) {
            LogUtil.exception("[exportFusionChart.jsp]delete fail!", ex);
        }
    }
%>
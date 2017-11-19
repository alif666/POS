package controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import model.SalesDetails;
import model.SalesInfo;

public class PositionPdf {
    private String FILE;
    int totalPrice;
    public void makePdf(long sessionId,String paymentType){
        try {
            
             Rectangle one = new Rectangle(300,700);
            //Rectangle two = new Rectangle(700,400);
            Font font = FontFactory.getFont("MyriadPro-Regular.ttf",
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8.0f, Font.NORMAL, BaseColor.BLACK);
            BaseFont baseFont = font.getBaseFont();
            FILE=sessionId+".pdf";
            String address="166,MirpurRoad, Kalabagan, Dhaka-1205";
            String vatRegNumber="184587844343";
            String hotLineNumber="0170128300";
            //PositionPdf p = new PositionPdf();
            UpdateOrderController updateOrder = new UpdateOrderController(sessionId);
            SalesInfo salesInfo = updateOrder.getSalesInfo();
            System.out.println("Card Amount is "+salesInfo.getCardAmount());
            System.out.println("Cash Amount is "+salesInfo.getCashAmount());
            System.out.println("Vat is "+salesInfo.getVat());
            List<SalesDetails> salesList =updateOrder.getSalesDetails();
           
            Document document = new Document();
            document.setPageSize(one);
            Paragraph spacing=new Paragraph("\n");
            //PdfWriter.getInstance(document, new FileOutputStream(FILE));
            PdfWriter.getInstance(document, new FileOutputStream("../BillNo_"+sessionId+"_bill.pdf"));
            document.open();
            //document.add(spacing);
            Image img = Image.getInstance("m-cafe.png");
            int indentation = 0;
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()- document.rightMargin() - indentation) / img.getWidth()) * 40;
            img.scalePercent(scaler);
            img.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            // Left
            String date = new SimpleDateFormat("dd MMM yyyy, HH:mm").format(Calendar.getInstance().getTime());
            //String date = "26 July 2017, 01:55";
            String header;
            if(salesInfo.getOrderType().equals("Take Away"))
            {
             header=address+"\nHot Line-"+hotLineNumber+"\nVAT REG No. "+vatRegNumber+"\nDate & Time: "+date+"\nInvoice No: mcafe-"+sessionId;

            }
            else{
                   header=address+"\nHot Line-"+hotLineNumber+"\nVAT REG No. "+vatRegNumber+"\nDate & Time: "+date+"\nTable No.: "+salesInfo.getTable().getTableName()+"                          Guest: "+salesInfo.getGuestNumber()+"\nInvoice No: mcafe-"+sessionId;
            }
            
            
         

            Font f1 = new Font(baseFont, 8.0f, Font.NORMAL, BaseColor.BLACK);
           
            Chunk c1 =new Chunk(header,f1);
            Paragraph paragraph = new Paragraph(c1);
            paragraph.setAlignment(Element.ALIGN_CENTER);
           
            document.add(paragraph);
            document.add(spacing);
            
            
            PdfPTable table = new PdfPTable(4);
            table.setWidths(new float[] {4, 2, 2, 2});
            //Table header
                Font f2 = new Font(baseFont, 8.0f, Font.BOLD, BaseColor.BLACK);
                Chunk c2 =new Chunk("NAME",f2);
                Paragraph n = new Paragraph(c2);
                PdfPCell nCell = new PdfPCell(n);
                nCell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(nCell);
                
        
                Chunk c3 =new Chunk("PRICE",f2);
                Paragraph p = new Paragraph(c3);
                PdfPCell pCell = new PdfPCell(p);
                pCell.setBorder(PdfPCell.NO_BORDER);
                pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(pCell);
                
                Chunk c4 =new Chunk("QTY",f2);
                Paragraph q = new Paragraph(c4);
                PdfPCell qtyCell = new PdfPCell(q);
                qtyCell.setBorder(PdfPCell.NO_BORDER);
                qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(qtyCell);
                
                Chunk c5 =new Chunk("Total",f2);
                Paragraph r = new Paragraph(c5);
                PdfPCell totalCell = new PdfPCell(r);
                totalCell.setBorder(PdfPCell.NO_BORDER);
                totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(totalCell);
            //Table header ends
 
                
            //alif is checking
            Font f3 = new Font(baseFont, 8.0f, Font.NORMAL, BaseColor.BLACK);
            for(SalesDetails sd:salesList){
                Chunk itemNameChunk =new Chunk(sd.getRecipe().getName(),f3);
                Paragraph itemName = new Paragraph(itemNameChunk);
                PdfPCell itemNameCell = new PdfPCell(itemName);
                itemNameCell.setBorder(PdfPCell.NO_BORDER);
                
                
                table.addCell(itemNameCell);
                
                Chunk itemPriceChunk =new Chunk(sd.getRecipe().getPrice(),f3);
                Paragraph price = new Paragraph(itemPriceChunk);
                PdfPCell priceCell = new PdfPCell(price);
                priceCell.setBorder(PdfPCell.NO_BORDER);
                priceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(priceCell);
                
                
                Chunk qtyChunk =new Chunk(sd.getQuantity()+"",f3);
                Paragraph quantity = new Paragraph(qtyChunk);
                PdfPCell quantityCell = new PdfPCell(quantity);
                quantityCell.setBorder(PdfPCell.NO_BORDER);
                quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(quantityCell);
                
                totalPrice=(int) (sd.getQuantity()*Integer.parseInt(sd.getRecipe().getPrice()));
                Chunk totalChunk =new Chunk(totalPrice+"",f3);
                Paragraph total = new Paragraph(totalChunk);
                PdfPCell totCell = new PdfPCell(total);
                totCell.setBorder(PdfPCell.NO_BORDER);
                totCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(totCell);
  
            }
            //Paragraph tParagraph=new Paragraph();
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            //table.setWidthPercentage(40);
            document.add(table);
            LineSeparator ls = new LineSeparator();
            ls.setLineColor(BaseColor.GRAY);
            ls.setLineWidth((float) 0.2);
            ls.setPercentage(80);
            ls.setAlignment(Element.ALIGN_CENTER);
            document.add(new Chunk(ls));
//            
            //Paragraph advancedHistory = new Paragraph("Advanced History\n");
            //document.add(advancedHistory);
            PdfPTable salesTable = new PdfPTable(2);
            salesTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            salesTable.setWidths(new float[] { 3, 1});
            

//subtotal
            Chunk subTotalChunk =new Chunk("Sub Total ",f2);
            Paragraph subTotal = new Paragraph(subTotalChunk);
            PdfPCell subTotalCell = new PdfPCell(subTotal);
            subTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            subTotalCell.setBorder(PdfPCell.NO_BORDER);
            salesTable.addCell(subTotalCell);
            Chunk subTotalAmountChunk =new Chunk(salesInfo.getSubTotal()+"",f2);
            Paragraph subTotalAmount = new Paragraph(subTotalAmountChunk);
            PdfPCell subTotalAmountCell = new PdfPCell(subTotalAmount);
            subTotalAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            subTotalAmountCell.setBorder(PdfPCell.NO_BORDER);
            salesTable.addCell(subTotalAmountCell);
            //subtotal ends//
            
            //vat
            Chunk vatChunk =new Chunk("VAT+SC ",f2);
            Paragraph vat = new Paragraph(vatChunk);
            PdfPCell vatCell = new PdfPCell(vat);
            vatCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            vatCell.setBorder(PdfPCell.NO_BORDER);
            salesTable.addCell(vatCell);
            Chunk vatAmountChunk =new Chunk(salesInfo.getVat()+"",f2);
            Paragraph vatAmount = new Paragraph(vatAmountChunk);
            PdfPCell vatAmountCell = new PdfPCell(vatAmount);
            vatAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            vatAmountCell.setBorder(PdfPCell.NO_BORDER);
            salesTable.addCell(vatAmountCell);
            //vat ends
            
            //Discount
            Chunk discountChunk =new Chunk("Discount ",f2);
            Paragraph discount = new Paragraph(discountChunk);
            PdfPCell discountCell = new PdfPCell(discount);
            discountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            discountCell.setBorder(PdfPCell.NO_BORDER);
            salesTable.addCell(discountCell);
            Chunk discountAmountChunk =new Chunk(salesInfo.getDiscount(),f2);
            Paragraph discountAmount = new Paragraph(discountAmountChunk);
            PdfPCell discountAmountCell = new PdfPCell(discountAmount);
            discountAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            discountCell.setBorder(PdfPCell.NO_BORDER);
            discountAmountCell.setBorder(PdfPCell.NO_BORDER);
            salesTable.addCell(discountAmountCell);
            //Discount ends

            //Order Total
            Chunk orderTotalChunk =new Chunk("Total",f2);
            Paragraph orderTotal = new Paragraph(orderTotalChunk);
            PdfPCell orderTotalCell = new PdfPCell(orderTotal);
            orderTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            orderTotalCell.setBorder(PdfPCell.NO_BORDER);
            salesTable.addCell(orderTotalCell);
            Chunk orderTotalAmountChunk =new Chunk(salesInfo.getOrderTotal()+"",f2);
            Paragraph orderTotalAmount = new Paragraph(orderTotalAmountChunk);
            PdfPCell orderTotalAmountCell = new PdfPCell(orderTotalAmount);
            orderTotalAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            orderTotalAmountCell.setBorder(PdfPCell.NO_BORDER);
            salesTable.addCell(orderTotalAmountCell);
            //Order Total ends
            document.add(salesTable);

            if(!paymentType.equals("") || !paymentType.equals("")){  
                LineSeparator ls1 = new LineSeparator();
                ls1.setLineColor(BaseColor.GRAY);
                ls1.setLineWidth((float) 0.2);
                ls1.setPercentage(80);
                ls1.setAlignment(Element.ALIGN_CENTER);
                document.add(new Chunk(ls1));
                
//                Chunk advancedHistoryChunk =new Chunk("ADVANCED HISTORY",f2);
//                Paragraph advancedHistory = new Paragraph(advancedHistoryChunk);
//                document.add(advancedHistory);
                
                PdfPTable salesTable1 = new PdfPTable(2);
                salesTable1.setHorizontalAlignment(Element.ALIGN_CENTER);
                salesTable1.setWidths(new float[] { 3, 1});
            //Paid Amount
                Chunk PaidTotalChunk =new Chunk("Paid Amount",f2);
                Paragraph paidTotal = new Paragraph(PaidTotalChunk);
                PdfPCell paidTotalCell = new PdfPCell(paidTotal);
                paidTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                paidTotalCell.setBorder(PdfPCell.NO_BORDER);
                salesTable1.addCell(paidTotalCell);
                Chunk paidTotalAmountChunk =new Chunk(salesInfo.getPaidAmount(),f2);
                Paragraph paidTotalAmount = new Paragraph(paidTotalAmountChunk);
                PdfPCell paidTotalAmountCell = new PdfPCell(paidTotalAmount);
                paidTotalAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                paidTotalAmountCell.setBorder(PdfPCell.NO_BORDER);
                salesTable1.addCell(paidTotalAmountCell);
                //Paid Amount ends

                //Returned Amount
                Chunk returnTotalChunk =new Chunk("Returned Amount ",f2);
                Paragraph returnTotal = new Paragraph(returnTotalChunk);
                PdfPCell returnTotalCell = new PdfPCell(returnTotal);
                returnTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                returnTotalCell.setBorder(PdfPCell.NO_BORDER);
                salesTable1.addCell(returnTotalCell);
                Chunk returnTotalAmountChunk =new Chunk(salesInfo.getReturnedAmount(),f2);
                Paragraph returnTotalAmount = new Paragraph(returnTotalAmountChunk);
                PdfPCell returnTotalAmountCell = new PdfPCell(returnTotalAmount);
                returnTotalAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                returnTotalAmountCell.setBorder(PdfPCell.NO_BORDER);
                salesTable1.addCell(returnTotalAmountCell);
                //Returned Amount ends

                //Bank Name And Card Amount
                if(paymentType.equals("card") || paymentType.equals("split")){
                    Chunk cardTotalChunk =new Chunk("Card Amount ",f2);
                    Paragraph cardTotal = new Paragraph(cardTotalChunk);
                    PdfPCell cardTotalCell = new PdfPCell(cardTotal);
                    cardTotalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cardTotalCell.setBorder(PdfPCell.NO_BORDER);
                    salesTable1.addCell(cardTotalCell);
                    Chunk cardTotalAmountChunk =new Chunk(salesInfo.getCardAmount(),f2);
                    Paragraph cardTotalAmount = new Paragraph(cardTotalAmountChunk);
                    PdfPCell cardTotalAmountCell = new PdfPCell(cardTotalAmount);
                    cardTotalAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cardTotalAmountCell.setBorder(PdfPCell.NO_BORDER);
                    salesTable1.addCell(cardTotalAmountCell);

                    Chunk bankNameChunk =new Chunk("Bank Name ",f2);
                    Paragraph bankName = new Paragraph(bankNameChunk);
                    PdfPCell bankNameCell = new PdfPCell(bankName);
                    bankNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    bankNameCell.setBorder(PdfPCell.NO_BORDER);
                    salesTable1.addCell(bankNameCell);
                    Chunk bankChunk =new Chunk(salesInfo.getBankName(),f2);
                    Paragraph bank = new Paragraph(bankChunk);
                    PdfPCell bankCell = new PdfPCell(bank);
                    bankCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    bankCell.setBorder(PdfPCell.NO_BORDER);
                    salesTable1.addCell(bankCell);
                }
            //Bank Name And Card Amount ends
            document.add(salesTable1);
            }
            //salesTable.setWidthPercentage(40);
            //salesTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
                if (Desktop.isDesktopSupported()) {
                    try {
                        File myFile = new File("../BillNo_"+sessionId+"_bill.pdf");
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        // no application registered for PDFs
                    }
                }

    }

    public static void main(String[] args) {
      
          //  p.makePdf();
        
    }

}

package XQBHController.FreeTest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class socket {
    public static void main(String[] args) throws IOException {
        while (true) {
            //1������һ����������Socket����ServerSocket��ָ���󶨵Ķ˿ڣ��������˶˿�
            ServerSocket serverSocket = new ServerSocket(9001);//1024-65535��ĳ���˿�
//2������accept()������ʼ�������ȴ��ͻ��˵�����
            Socket socket = serverSocket.accept();
//3����ȡ������������ȡ�ͻ�����Ϣ
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("���Ƿ��������ͻ���˵��" + info);
            }
            socket.shutdownInput();//�ر�������
//4����ȡ���������Ӧ�ͻ��˵�����
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("��ӭ����");
            pw.flush();


//5���ر���Դ
            pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();
            socket.close();
            serverSocket.close();
        }
    }
}

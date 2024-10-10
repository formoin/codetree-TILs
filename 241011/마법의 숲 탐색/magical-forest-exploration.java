import java.io.*;
import java.util.*;


public class Main {
    static int R, C, K;
    static int[][] fairy;

    static int[][] dir = {
        {-1, 0, 1, 0},
        {0, 1, 0, -1}
        // 북 동 남 서
    };
    static int[][] board;
    static int answer=0;
    static int cur_ans =0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        board = new int[R+1][C+1];
        fairy = new int[K][2];

        for(int i=0; i<K; i++) {
            st = new StringTokenizer(br.readLine());
            fairy[i][0] = Integer.parseInt(st.nextToken());
            fairy[i][1] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<K; i++){
            down(i);
        }
        System.out.println(answer);
    }

    public static void down(int i) {
        int r =1;
        int c = fairy[i][0];
        while(true) {
            boolean flag = true;
            int nr = r+1;
            int nc = c;
            for(int d=1; d<4; d++) {
                int dest_nr = nr+dir[0][d];
                int dest_nc = nc+dir[1][d];

                if( dest_nr>R || dest_nc<1 || dest_nc>C || board[dest_nr][dest_nc] != 0) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                r = nr;
                continue;
            }
            
            flag = true;
            nr=r;
            nc=c-1;
            for(int d=0; d<4; d++) {
                int dest_nr = nr+dir[0][d];
                int dest_nc = nc+dir[1][d];

                if(dest_nr>R || dest_nc<1 || board[dest_nr][dest_nc] != 0) {
                    flag = false;
                    break;
                }
            }
            nr=r+1;
            nc=c-1;
            if(nr+1>R || nc-1<1 || board[nr+1][nc]!=0 || board[nr][nc-1]!=0) {
                flag = false;
            }
            if(flag) {
                r = nr;
                c = nc;
                fairy[i][1] =(fairy[i][1]+3)%4;
                continue;
            }


            flag = true;
            nr=r;
            nc=c+1;
            for(int d=0; d<4; d++) {
                int dest_nr = nr+dir[0][d];
                int dest_nc = nc+dir[1][d];

                if(dest_nr>R || dest_nc>C || board[dest_nr][dest_nc] != 0) {
                    flag = false;
                    break;
                }
            }
            nr=r+1;
            nc=c+1;
            if(nr+1>R || nc+1>C || board[nr+1][nc]!=0 || board[nr][nc+1]!=0) {
                flag = false;
            }
            if(flag) {
                r = nr;
                c = nc;
                fairy[i][1] =(fairy[i][1]+1)%4;
                continue;
            }
            break;
        }

        if(r<=1) {
            board= new int[R+1][C+1];
        }
        else {
            board[r][c] =i+1;
            for(int d=0; d<4; d++) {
                int nr = r+dir[0][d];
                int nc = c+dir[1][d];
                board[nr][nc] = (i+1) ;
                if(d==fairy[i][1]) board[nr][nc] = K+1+i;
            }

            boolean[][] visit = new boolean[R+1][C+1];
            visit[r][c] = true;

            cur_ans =0;
            move_fairy(r, c, visit, i+1);
            answer+= cur_ans;
        }

    }

    static void move_fairy(int r, int c, boolean[][] visit, int i) {



        if(board[r][c]<=K) i=board[r][c];
        for(int d=0; d<4; d++) {
            int nr = r+dir[0][d];
            int nc = c+dir[1][d];
            if(nr<1 || nr>R || nc<1 || nc>C || visit[nr][nc]) continue;

            if(board[r][c]!=K+i) {
                if( board[nr][nc] != i && board[nr][nc]!=K+i) continue;
            }

            if(board[nr][nc] == 0) continue;

            visit[nr][nc] =true;
            move_fairy(nr,nc, visit, i);
            visit[nr][nc] = false;
        }
        
        cur_ans = Math.max(cur_ans, r);

    }
}
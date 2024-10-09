import java.io.*;
import java.util.*;

public class Main {
    static int[][] board;
    static int[] rocks;
    static int rock_cnt=0;
    static int T, M;
    public static int[][] dir = {
        {0, 1, 0, -1},
        {1, 0, -1, 0}
    };
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
         T = Integer.parseInt(st.nextToken());
         M = Integer.parseInt(st.nextToken());
        board = new int[5][5];
        rocks = new int[M];
        
        for(int i=0; i<5; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<5; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<M; i++) {
            rocks[i] = Integer.parseInt(st.nextToken());
           
        }

        int[][] answer_board = new int[5][5];
        int answer_value =0;
        for(int t=0; t<T; t++) {
            answer_value =0;
            for(int angle = 3; angle>0; angle--) {
                for(int c = 3; c>=1; c--) {
                    for(int r=3; r>=1; r--) {
                        int[][] temp =spin(r, c, angle);

                        int cmp = get_value(temp);
                        if(cmp>= answer_value) {
                          
                            answer_board = temp;
                            answer_value = cmp;
                        }
                    }
                }
            }
            if(answer_value == 0) break;
            answer_value += react_chain(answer_board);

            sb.append(answer_value+ " ");
        }
        System.out.println(sb);
    }

    private static int[][] spin(int r, int c, int angle) {
        Deque<Integer> q = new ArrayDeque<>();
        int[][] spined_board = new int[5][5];
        for(int i=0; i<5; i++) {
            System.arraycopy(board[i], 0, spined_board[i], 0, board[i].length);
        }
        int nr = r-1;
        int nc = c-1;

        for(int d=0; d<4; d++) {
            for(int i=0; i<2; i++) {
                
                nr += dir[0][d];
                nc += dir[1][d];

                q.add(board[nr][nc]);
            }
        }

        int d = angle;
        if(angle == 1) {
            nr = r-1;
            nc = c+1;
        }
        else if(angle == 2) {
            nr = r+1;
            nc = c+1;
        }
        else {
            nr = r+1;
            nc = c-1;
        }
        while(!q.isEmpty()) {
            
            for(int i=0; i<2; i++) {
                
                nr += dir[0][d];
                nc += dir[1][d];

                if(nr<0|| nr>=5|| nc<0 || nc>=5) continue;
                spined_board[nr][nc] = q.poll();
            }
            d = (d+1)%4;
            
        }

        return spined_board;
    }

    private static int get_value(int[][] spined_board) {
        
        Deque<Integer[]> q = new ArrayDeque<>();
        Deque<Integer[]> q_for = new ArrayDeque<>();
        boolean[][] visit = new boolean[5][5];
        int total =0;
        int target;
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                
                if(visit[i][j]) continue;

                q_for.clear();
                target = spined_board[i][j];

                q.add(new Integer[] {i, j});
                q_for.add(new Integer[] {i, j});
                visit[i][j] = true;

                while(!q.isEmpty() ) {
                    Integer[] buf = q.poll();
                    int r =buf[0];
                    int c =buf[1];

                    for(int d=0; d<4; d++) {
                        int nr = r+dir[0][d];
                        int nc = c+dir[1][d];

                        if(nr<0 || nr>=5 || nc<0 || nc>=5) continue;
                        if(visit[nr][nc] || spined_board[nr][nc]!=target) continue;

                        visit[nr][nc] = true;
                        q.add(new Integer[] {nr, nc});
                        q_for.add(new Integer[] {nr, nc});

                    }
                }
                if(q_for.size()>=3) {
                    total+=q_for.size();
                    while(!q_for.isEmpty()) {
                        Integer[] tmep = q_for.poll();
                        spined_board[tmep[0]][tmep[1]] = 0;
                    }
                }

            }
        }
        
        return total;
    }

    private static void set_board(int[][] setting_board) {
        for(int c=0; c<5; c++) {
            for(int r=4; r>=0; r--) {
                if(setting_board[r][c] == 0) {
                    
                    setting_board[r][c] = rocks[rock_cnt++];
                }
            }
        }
        board = setting_board;
    }

    private static int react_chain(int[][] board) {
        int num = Integer.MAX_VALUE;
        int total = 0;
        while(num !=0) {


            set_board(board);

            num = get_value(board);
            total += num;

        }


        return total;
    }
}
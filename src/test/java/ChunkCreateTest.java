import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.request.ChunkCreateRequest;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

public class ChunkCreateTest {
    protected Retrofit m_retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8009/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    protected String m_rootPath = System.getProperty("user.home")
            + File.separator + "RestTerminal"+ File.separator;
    ChunkService m_chunkService = m_retrofit.create(ChunkService.class);
    @Test
    public void chunkCreateBadRequest() {
        ChunkCreateRequest chunkCreateRequest = new ChunkCreateRequest("notANode", 10);
        Call<Message> call = m_chunkService.chunkCreate(chunkCreateRequest);
        Response<Message> response = null;
        try {
            response = call.execute();
        } catch (IOException p_e) {
            p_e.printStackTrace();
        }
        if(response.isSuccessful()) {
            assertEquals(1,0);
        }
        else {
            APIError error = ErrorUtils.parseError(response, m_retrofit);
            Message compareMessage = new Message("Invalid NodeID");
            assertEquals(error.getError(), compareMessage.getMessage());
        }

    }
}

import {useEffect, useState} from "react";
import {MDBCol, MDBContainer, MDBRow, MDBTable, MDBTableBody, MDBTableHead} from "mdb-react-ui-kit";
import ClubService from "../../services/ClubService";

export default function TopClubs() {

    const [clubsInfo, setClubsInfo] = useState(null);
    const [errorMsg, setErrorMsg] = useState(null);

    useEffect(() => {
        ClubService.getTopPopularClubs(10).then(
            (response) => setClubsInfo(response.data),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );
    });

    const topClubItem = (club) => {
      return(
          <tr>
              <td>{club.name}</td>
              <td>@{club.uniqueName}</td>
              <td>{club.participantsNumber}</td>
              <td>{club.clubManager}</td>
          </tr>
      );
    }

    return (
        <MDBContainer>
            <h2 className="text-center p-4">Top popular clubs</h2>
            <MDBRow className="justify-content-center">
                <MDBCol md={8}>
                    <MDBTable striped>
                        <MDBTableHead>
                            <tr>
                                <th>Club name</th>
                                <th>Club uniquename</th>
                                <th>Participants</th>
                                <th>Club manager</th>
                            </tr>
                        </MDBTableHead>
                        <MDBTableBody>
                            {/*{clubsInfo ? clubsInfo.map((club) => topClubItem(club)) : <p> No top clubs </p>}*/}
                        </MDBTableBody>
                    </MDBTable>
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    )
}

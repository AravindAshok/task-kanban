//import "bootstrap/dist/css/bootstrap.min.css";
import "bootswatch/dist/darkly/bootstrap.min.css";

import { Navbar, Nav, Container, Row, Col, Card } from "react-bootstrap";

const React = require('react');
const ReactDOM = require('react-dom');

const STATUS_LIST = [
  { status: "CREATED", name: "created" },
  { status: "TO_DO", name: "todo" },
  { status: "IN_PROGRESS", name: "inprogress" },
  { status: "DONE", name: "done" }
];

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {activeStatus: STATUS_LIST[1].status};
	}

	render() {
	  const activeStatus = this.state.activeStatus;
    return (
      <div>
        <Navbar bg="primary">
          <Navbar.Brand><h1>Chore Kanban</h1></Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
        </Navbar>
        <br/>
        <Container>
          <Row>
            <Col md={4} sm={4}>
              <h3>Status</h3>
              <Nav
                variant="pills"
                className="flex-column"
                activeKey={activeStatus}
                onSelect = {
                  (status) => {
                    this.setState({activeStatus: status});
                  }
                }
               >
                <Nav.Item>
                  <Nav.Link eventKey="TO_DO">TO-DO</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link eventKey="IN_PROGRESS">IN PROGRESS</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link eventKey="DONE">DONE</Nav.Link>
                </Nav.Item>
              </Nav>
            </Col>
            <Col md={8} sm={8}>
              <TaskList key={activeStatus} status={activeStatus} />
            </Col>
          </Row>
        </Container>
      </div>
    );
	}
}

class TaskList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {tasks: []};
	}

	componentDidMount() {
	  const status = this.props.status;
	  const URL = "http://localhost:8081/tasks/tasksByStatus?status=" + status;
	  fetch(URL)
      .then(response => response.json())
      .then(data => this.setState({tasks: data}));
	}

	render() {
      const tasksFinal = this.state.tasks.map(task =>
        {
          const taskKey = task.title + task.status;
          console.log("Task key: " + taskKey);
          return <p><Task key={taskKey} task={task}/></p>
        }
      );
	    return (
	      tasksFinal
	    )
	}

}

class Task extends React.Component{
	render() {
		return (
		    <Card className="text-center" style={{ width: '18rem' }}>
        <Card.Body>
          <Card.Title>{this.props.task.title}</Card.Title>
          <Card.Text>{this.props.task.description}</Card.Text>
          <Card.Link href="#">IN PROGRESS</Card.Link>
          <Card.Link href="#">DONE</Card.Link>
        </Card.Body>
        <Card.Footer className="text-muted">{this.props.task.points} Points</Card.Footer>
      </Card>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
//import "bootstrap/dist/css/bootstrap.min.css";
import "bootswatch/dist/darkly/bootstrap.min.css";

import { Navbar, Nav, Container, Row, Col, Card, Button } from "react-bootstrap";

const React = require('react');
const ReactDOM = require('react-dom');

const BASE_URL = "http://localhost:8081"

const STATUS_CREATED = "CREATED";
const TO_DO = "TO DO";
const IN_PROGRESS = "IN PROGRESS";
const DONE = "DONE";

const STATUSES_VALUE_TO_ENUM = {
  "CREATED": "CREATED",
  "TO DO": "TO_DO",
  "IN PROGRESS": "IN_PROGRESS",
  "DONE": "DONE"
}

const STATUSES_ENUM_TO_VALUE = {
  "CREATED": "CREATED",
  "TO_DO": "TO DO",
  "IN_PROGRESS": "IN PROGRESS",
  "DONE": "DONE"
}

const NEXT_STATUS = {
  "CREATED": "TO DO",
  "TO DO": "IN PROGRESS",
  "IN PROGRESS": "DONE",
  "DONE": "TO DO"
}

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {activeStatus: STATUSES_VALUE_TO_ENUM[TO_DO]};
		this.handleActiveStatusChange = this.handleActiveStatusChange.bind(this);
	}

	handleActiveStatusChange(newStatus) {
	  this.setState({activeStatus: newStatus});
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
                  <Nav.Link eventKey={STATUSES_VALUE_TO_ENUM[TO_DO]}>{TO_DO}</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link eventKey={STATUSES_VALUE_TO_ENUM[IN_PROGRESS]}>{IN_PROGRESS}</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link eventKey={STATUSES_VALUE_TO_ENUM[DONE]}>{DONE}</Nav.Link>
                </Nav.Item>
              </Nav>
            </Col>
            <Col md={8} sm={8}>
              <TaskList key={activeStatus} status={activeStatus} onActiveStatusChange={this.handleActiveStatusChange} />
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
		this.state = {tasks: null};
		this.handleActiveStatusChange = this.handleActiveStatusChange.bind(this);
	}

	handleActiveStatusChange(newStatus) {
	  this.props.onActiveStatusChange(newStatus);
	}

	componentDidMount() {
	  const status = this.props.status;
	  const URL = BASE_URL + "/tasks/tasksByStatus?status=" + status;
	  fetch(URL, {headers: {
       'Cache-Control': 'no-cache'
     }})
    .then(response => response.json())
    .then(data => this.setState({tasks: data}));
	}

	render() {
    if (!this.state.tasks) {
      return <p>Loading</p>
    }

    const tasksFinal = this.state.tasks.map(task =>
      {
        const taskKey = task.title + task.status;
        return <p><Task key={taskKey} task={task} onActiveStatusChange={this.handleActiveStatusChange} /></p>
      }
    );
    return (
      tasksFinal
    )
	}

}

class Task extends React.Component{
  constructor() {
    super()
    this.state = {task: null}
    this.updateStatusHandler = this.updateStatusHandler.bind(this)
    this.handleActiveStatusChange = this.handleActiveStatusChange.bind(this);
    this.updateStatusAPICall = this.updateStatusAPICall.bind(this);
    this.updateStatusPromise = this.updateStatusPromise.bind(this);
  }

  componentDidMount() {
	  this.setState({task: this.props.task})
  }

  handleActiveStatusChange(newStatus) {
    this.props.onActiveStatusChange(newStatus);
  }

  updateStatusHandler() {
    var task = this.state.task;
    const newStatus = STATUSES_VALUE_TO_ENUM[NEXT_STATUS[STATUSES_ENUM_TO_VALUE[this.state.task.status]]];
    this.updateStatusPromise(task.id, newStatus)
      .then(() => this.handleActiveStatusChange(newStatus));
  }

  updateStatusPromise(taskId, newStatus) {
    return Promise.all([this.updateStatusAPICall(taskId, newStatus)])
  }

  updateStatusAPICall(taskId, newStatus) {
    const updateStatusEndpoint = BASE_URL + "/tasks/" + taskId + "/updateStatus?status=" + newStatus;
    return fetch(updateStatusEndpoint,
      {
        method: 'post'
      })
      .then(response => response.json);
  }

	render() {
	  if(!this.state.task) return <p>Loading...</p>
	  const nextStatus = NEXT_STATUS[STATUSES_ENUM_TO_VALUE[this.state.task.status]];
		return (
		    <Card className="text-center" style={{ width: '18rem' }}>
        <Card.Body>
          <Card.Title>{this.state.task.title}</Card.Title>
          <Card.Text>{this.state.task.description}</Card.Text>
          <Button onClick={this.updateStatusHandler}>{nextStatus}</Button>
        </Card.Body>
        <Card.Footer className="text-muted">{this.state.task.points} Points</Card.Footer>
      </Card>
		)
	}
}


ReactDOM.render(
	<App />,
	document.getElementById('react')
)